package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import java.time.LocalDate

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionEvent {

    object CloseScreen : TransactionEvent()

    class NavigateToCategoryPickerScreen(
        val screenArguments: CategoriesListScreenArguments
    ) : TransactionEvent()

    class ShowDatePicker(
        val localDate: LocalDate
    ) : TransactionEvent()

}