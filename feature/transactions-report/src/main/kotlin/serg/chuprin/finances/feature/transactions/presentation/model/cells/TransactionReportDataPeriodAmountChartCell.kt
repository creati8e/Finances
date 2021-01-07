package serg.chuprin.finances.feature.transactions.presentation.model.cells

import androidx.annotation.IntRange
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
data class TransactionReportDataPeriodAmountChartCell(
    val dataPeriod: DataPeriod,
    val formattedAmount: String,
    val formattedPeriodName: String,
    @IntRange(from = 0, to = 100)
    val barFill: Int,
    val isChosen: Boolean
) : DiffCell<DataPeriod> {

    class ChangedPayload

    override val diffCellId: DataPeriod = dataPeriod

}