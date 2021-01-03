package serg.chuprin.finances.feature.transaction.presentation.model.store

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionIntent {

    object ClickOnSaveButton : TransactionIntent()

    object ClickOnCloseButton : TransactionIntent()

    class EnterAmount(
        val amount: String
    ) : TransactionIntent()

}