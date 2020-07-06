package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReportFilter

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
sealed class TransactionsReportEffect {

    class ReportBuilt(
        val cells: List<BaseCell>,
        val filter: TransactionsReportFilter
    ) : TransactionsReportEffect()

}