package serg.chuprin.finances.feature.transaction.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.moneyaccount.query.MoneyAccountsQuery
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
// TODO: Format category as parent/children.
class TransactionStoreBootstrapper @Inject constructor(
    private val userRepository: UserRepository,
    private val screenArguments: TransactionScreenArguments,
    private val transactionRepository: TransactionRepository,
    private val moneyAccountRepository: MoneyAccountRepository
) : StoreBootstrapper<TransactionAction> {

    override fun invoke(): Flow<TransactionAction> {
        return flowOfSingleValue {
            val userId = userRepository.getCurrentUser().id

            return@flowOfSingleValue when (screenArguments) {
                is TransactionScreenArguments.Editing -> {
                    bootstrapForExistingTransaction(screenArguments.transactionId, userId)
                }
                is TransactionScreenArguments.Creation -> {
                    TransactionAction.FormatInitialState(
                        category = null,
                        userId = userId,
                        date = LocalDate.now(),
                        amount = BigDecimal.ZERO,
                        moneyAccount = getFirstFavoriteMoneyAccount(userId),
                        operation = TransactionChosenOperation.Plain(PlainTransactionType.EXPENSE)
                    )
                }
            }
        }
    }

    private suspend fun bootstrapForExistingTransaction(
        transactionId: Id,
        userId: Id
    ): TransactionAction {
        val transaction = transactionRepository
            .transactionsFlow(
                TransactionsQuery(
                    ownerId = userId,
                    transactionIds = setOf(transactionId)
                )
            )
            .first()
            .first()

        // TODO: Remove .toLocalDate from here.
        return TransactionAction.FormatInitialStateForExistingTransaction(
            userId = userId,
            categoryId = transaction.categoryId,
            date = transaction.dateTime.toLocalDate(),
            // Transaction may contains "-" at amount beginning. Remove it.
            amount = transaction.amount.abs(),
            moneyAccountId = transaction.moneyAccountId,
            operation = TransactionChosenOperation.Plain(
                if (transaction.isIncome) {
                    PlainTransactionType.INCOME
                } else {
                    PlainTransactionType.EXPENSE
                }
            )
        )
    }

    private suspend fun getFirstFavoriteMoneyAccount(userId: Id): MoneyAccount {
        val moneyAccounts = moneyAccountRepository
            .accountsFlow(MoneyAccountsQuery(ownerId = userId))
            .first()
            .sortedBy(MoneyAccount::name)

        return moneyAccounts
            .firstOrNull(MoneyAccount::isFavorite)
            ?: moneyAccounts.first()
    }

}