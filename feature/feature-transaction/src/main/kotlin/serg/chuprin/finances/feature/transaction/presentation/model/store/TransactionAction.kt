package serg.chuprin.finances.feature.transaction.presentation.model.store

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionAction {

    class ExecuteIntent(
        val intent: TransactionIntent
    ) : TransactionAction()

}