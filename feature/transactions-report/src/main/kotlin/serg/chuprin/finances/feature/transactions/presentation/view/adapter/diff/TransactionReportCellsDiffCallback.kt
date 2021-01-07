package serg.chuprin.finances.feature.transactions.presentation.view.adapter.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.SmartDiffCallback
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategorySharesCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportCategorySharesCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartListCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodAmountChartCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodSummaryCell

/**
 * Created by Sergey Chuprin on 22.12.2020.
 */
class TransactionReportCellsDiffCallback : SmartDiffCallback<BaseCell>() {

    init {
        addItemsComparator<ZeroDataCell> { _, _ -> true }

        addContentsComparator(ZeroDataCell::equals)

        addPayloadProvider<TransactionReportChartListCell> { _, _ ->
            TransactionReportChartListCell.ChangedPayload()
        }
        addPayloadProvider<TransactionReportDataPeriodAmountChartCell> { _, _ ->
            TransactionReportDataPeriodAmountChartCell.ChangedPayload()
        }
        addPayloadProvider<TransactionReportDataPeriodSummaryCell> { _, _ ->
            TransactionReportDataPeriodSummaryCell.ChangedPayload()
        }
        addPayloadProvider<TransactionReportCategorySharesCell> { _, _ ->
            CategorySharesCell.ChangedPayload()
        }
    }

}