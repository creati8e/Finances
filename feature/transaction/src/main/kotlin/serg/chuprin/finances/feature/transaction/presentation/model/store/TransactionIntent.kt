package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import java.time.LocalDate

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionIntent {

    object ClickOnDate : TransactionIntent()

    object ClickOnCategory : TransactionIntent()

    object ClickOnSaveButton : TransactionIntent()

    object ClickOnCloseButton : TransactionIntent()

    object ClickOnMoneyAccount : TransactionIntent()

    object ClickOnDeleteTransaction : TransactionIntent()

    object ClickOnConfirmTransactionDeletion : TransactionIntent()

    object ClickOnUnsavedChangedDialogNegativeButton : TransactionIntent()

    class EnterAmount(
        val amount: String
    ) : TransactionIntent()

    class ChooseCategory(
        val categoryId: Id
    ) : TransactionIntent()

    class ClickOnOperationType(
        val operation: TransactionChosenOperation
    ) : TransactionIntent()

    class ChooseDate(
        val localDate: LocalDate
    ) : TransactionIntent()

    class ChooseMoneyAccount(
        val moneyAccountId: Id
    ) : TransactionIntent()

}