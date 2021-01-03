package serg.chuprin.finances.feature.transaction.presentation.model.store

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionEvent {

    object CloseScreen : TransactionEvent()

}