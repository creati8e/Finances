package serg.chuprin.finances.feature.transactions.presentation.view.adapter.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.SmartDiffCallback
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartListCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodSummaryCell
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.diff.payload.TransactionReportDataPeriodAmountsChartListChangedPayload
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.diff.payload.TransactionReportDataPeriodSummaryChangedPayload

/**
 * Created by Sergey Chuprin on 22.12.2020.
 */
class TransactionReportCellsDiffCallback : SmartDiffCallback<BaseCell>() {

    init {
        addPayloadProvider<TransactionReportChartListCell> { _, _ ->
            TransactionReportDataPeriodAmountsChartListChangedPayload()
        }
        addPayloadProvider<TransactionReportDataPeriodSummaryCell> { _, _ ->
            TransactionReportDataPeriodSummaryChangedPayload()
        }
    }

}