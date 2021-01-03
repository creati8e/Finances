package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionEvent {

    object CloseScreen : TransactionEvent()

    class NavigateToCategoryPickerScreen(
        val screenArguments: CategoriesListScreenArguments
    ) : TransactionEvent()

}