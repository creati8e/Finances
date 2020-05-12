package serg.chuprin.finances.feature.transactions.presentation.model.store

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
sealed class TransactionsReportAction {

    class ExecuteIntent(
        val intent: TransactionsReportIntent
    ) : TransactionsReportAction()

}