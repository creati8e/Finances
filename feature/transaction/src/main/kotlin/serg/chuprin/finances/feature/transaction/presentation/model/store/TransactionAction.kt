package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionAction {

    class ExecuteIntent(
        val intent: TransactionIntent
    ) : TransactionAction()

    class FormatInitialState(
        val userId: Id,
        val date: LocalDate,
        val amount: BigDecimal,
        val moneyAccount: MoneyAccount,
        val category: CategoryWithParent?,
        val operation: TransactionChosenOperation
    ) : TransactionAction()

    class FormatInitialStateForExistingTransaction(
        val userId: Id,
        val dateTime: LocalDateTime,
        val amount: BigDecimal,
        val categoryId: Id?,
        val moneyAccountId: Id,
        val operation: TransactionChosenOperation
    ) : TransactionAction()

}