package serg.chuprin.finances.feature.moneyaccount.details.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.TransactionCategoriesMap
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.extensions.amount
import serg.chuprin.finances.feature.moneyaccount.details.domain.model.MoneyAccountDetails
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class GetMoneyAccountDetailsUseCase @Inject constructor(
    private val moneyAccountRepository: MoneyAccountRepository,
    private val transactionsByDayGrouper: TransactionsByDayGrouper,
    private val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
) {

    /**
     * @return flow of null if money account gets deleted.
     */
    fun execute(moneyAccountId: Id): Flow<MoneyAccountDetails?> {
        return moneyAccountRepository
            .accountFlow(moneyAccountId)
            .flatMapLatest { moneyAccount ->
                if (moneyAccount != null) {
                    @Suppress("MoveLambdaOutsideParentheses")
                    combine(
                        flowOf(moneyAccount),
                        transactionCategoryRetrieverService.transactionsFlow(
                            TransactionsQuery(moneyAccountIds = setOf(moneyAccount.id))
                        ),
                        { t1, t2 -> buildModel(t1, t2) }
                    )
                } else {
                    flowOf(null)
                }
            }
    }

    private fun buildModel(
        moneyAccount: MoneyAccount,
        transactionCategories: TransactionCategoriesMap
    ): MoneyAccountDetails {
        return MoneyAccountDetails(
            moneyAccount = moneyAccount,
            balance = transactionCategories.amount,
            transactionsGroupedByDay = transactionsByDayGrouper.group(transactionCategories)
        )
    }

}