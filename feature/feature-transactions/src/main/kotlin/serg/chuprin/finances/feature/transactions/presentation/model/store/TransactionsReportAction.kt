package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReport

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
sealed class TransactionsReportAction {

    class ExecuteIntent(
        val intent: TransactionsReportIntent
    ) : TransactionsReportAction()

    class FormatReport(
        val report: TransactionsReport
    ) : TransactionsReportAction()

}