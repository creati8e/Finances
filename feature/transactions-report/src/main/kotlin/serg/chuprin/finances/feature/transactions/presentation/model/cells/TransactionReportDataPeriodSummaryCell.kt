package serg.chuprin.finances.feature.transactions.presentation.model.cells

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 22.12.2020.
 */
data class TransactionReportDataPeriodSummaryCell(
    val title: String,
    val value: String
) : BaseCell {

    class ChangedPayload

}