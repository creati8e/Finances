package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments
import java.time.LocalDate

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionEvent {

    object CloseScreen : TransactionEvent()

    object ShowTransactionDeletionDialog : TransactionEvent()

    class ShowUnsavedChangedDialog(
        val message: String
    ) : TransactionEvent()

    class NavigateToCategoryPickerScreen(
        val screenArguments: CategoriesListScreenArguments
    ) : TransactionEvent()

    class NavigateToMoneyAccountPickerScreen(
        val screenArguments: MoneyAccountsListScreenArguments
    ) : TransactionEvent()

    class ShowDatePicker(
        val localDate: LocalDate
    ) : TransactionEvent()

}