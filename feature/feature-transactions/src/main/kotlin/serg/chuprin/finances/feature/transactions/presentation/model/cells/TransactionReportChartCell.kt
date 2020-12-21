package serg.chuprin.finances.feature.transactions.presentation.model.cells

import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
data class TransactionReportChartCell(
    val dataPeriod: DataPeriod,
    val formattedAmount: String,
    val formattedPeriodName: String
) : DiffCell<DataPeriod> {

    override val diffCellId: DataPeriod = dataPeriod

}