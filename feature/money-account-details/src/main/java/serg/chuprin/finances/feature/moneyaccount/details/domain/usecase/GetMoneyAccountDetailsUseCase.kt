package serg.chuprin.finances.feature.moneyaccount.details.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionCategoriesMap
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.extensions.amount
import serg.chuprin.finances.feature.moneyaccount.details.domain.model.MoneyAccountDetails
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class GetMoneyAccountDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val moneyAccountRepository: MoneyAccountRepository,
    private val transactionsByDayGrouper: TransactionsByDayGrouper,
    private val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
) {

    /**
     * @return flow of null if money account gets deleted.
     */
    fun execute(moneyAccountId: Id): Flow<MoneyAccountDetails?> {
        return combine(
            userRepository.currentUserSingleFlow(),
            moneyAccountRepository.accountFlow(moneyAccountId),
            ::Pair
        ).flatMapLatest { (user, moneyAccount) ->
            if (moneyAccount == null) {
                flowOf(null)
            } else {
                combine(
                    flowOf(moneyAccount),
                    transactionCategoryRetrieverService.transactionsFlow(
                        user.id,
                        TransactionsQuery(
                            ownerId = user.id,
                            moneyAccountIds = setOf(moneyAccount.id)
                        ),
                    ),
                    ::buildMoneyAccountDetails
                )
            }
        }
    }

    private fun buildMoneyAccountDetails(
        moneyAccount: MoneyAccount,
        categories: TransactionCategoriesMap
    ): MoneyAccountDetails {
        return MoneyAccountDetails(
            moneyAccount = moneyAccount,
            balance = categories.amount,
            transactionsGroupedByDay = transactionsByDayGrouper.group(categories)
        )
    }

}