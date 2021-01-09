package serg.chuprin.finances.feature.transactions.domain.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.moneyaccount.query.MoneyAccountsQuery
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.feature.transactions.domain.builder.TransactionReportDataPeriodAmountsBuilder
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportRawData
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class TransactionReportDataService @Inject constructor(
    private val userRepository: UserRepository,
    private val moneyAccountRepository: MoneyAccountRepository,
    private val filterRepository: TransactionReportFilterRepository,
    private val categoriesDataService: TransactionReportCategoriesDataService,
    private val transactionDataService: TransactionReportTransactionDataService,
    private val dataPeriodAmountsBuilder: TransactionReportDataPeriodAmountsBuilder,
    private val currentPeriodDataService: TransactionReportCurrentPeriodDataService
) {

    fun dataFlow(): Flow<TransactionReportRawData> {
        return flow {
            coroutineScope {

                // distinctUntilChanged here prevents just from duplicate data emissions.
                val filterFlow = filterRepository.filterFlow.distinctUntilChanged()

                // Firstly we need to retrieve categories to associate them with transactions.
                val categoriesFlow = categoriesDataService
                    .dataFlow(filterFlow)
                    .share(coroutineScope = this)

                // Then we need to retrieve all transactions for all time which are matches filter.
                // This is required for building historical data period amounts.
                //
                // Cache flow's to reduce overhead: when current data period in filter changed,
                // we need to retrieve transactions for that data period
                // (preserving chart data and do not rebuild it).
                val transactionsFlow = transactionDataService
                    .dataFlow(
                        filterFlow = filterFlow,
                        categoryIdToCategoryFlow = categoriesFlow
                    )
                    .share(coroutineScope = this)


                val moneyAccountsFlow = combine(
                    transactionsFlow,
                    userRepository.currentUserSingleFlow()
                ) { transactions, user ->
                    MoneyAccountsQuery(
                        ownerId = user.id,
                        accountIds = transactions.mapTo(mutableSetOf(), Transaction::moneyAccountId)
                    )
                }
                    .distinctUntilChanged()
                    .flatMapLatest(moneyAccountRepository::accountsFlow)
                    .map { moneyAccounts -> moneyAccounts.associateBy(MoneyAccount::id) }

                emitAll(
                    combine(
                        filterFlow,
                        moneyAccountsFlow,
                        dataPeriodAmountsBuilder.dataFlow(
                            filterFlow = filterFlow,
                            transactionsFlow = transactionsFlow
                        ),
                        // Filter and list data emissions must be zipped because list data is updated
                        // when filter is updated. This is required for preventing simultaneous emissions.
                        currentPeriodDataService.dataFlow(
                            filterFlow = filterFlow,
                            categoriesFlow = categoriesFlow,
                            transactionsFlow = transactionsFlow
                        )
                    ) { filter, moneyAccounts, dataPeriodAmounts, currentPeriodData ->
                        TransactionReportRawData(
                            filter = filter,
                            moneyAccounts = moneyAccounts,
                            dataPeriodAmounts = dataPeriodAmounts,
                            categoryToTransactionsList = currentPeriodData.categoryToTransactionsList,
                            transactionToCategory = currentPeriodData.transactionToCategory
                        )
                    }.debounce(100)
                )
            }
        }
    }

    private fun <T> Flow<T>.share(coroutineScope: CoroutineScope): SharedFlow<T> {
        return shareIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(replayExpirationMillis = 0L)
        )
    }

}