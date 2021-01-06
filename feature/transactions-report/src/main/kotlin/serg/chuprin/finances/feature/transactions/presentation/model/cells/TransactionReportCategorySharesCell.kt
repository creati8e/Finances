package serg.chuprin.finances.feature.transactions.presentation.model.cells

import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategorySharesCell
import serg.chuprin.finances.core.piechart.model.PieChartDataPart

/**
 * Created by Sergey Chuprin on 24.12.2020.
 */
data class TransactionReportCategorySharesCell(
    override val label: String,
    override val totalAmount: String,
    override val chartParts: List<PieChartDataPart>,
    override val transactionType: PlainTransactionType,
    override val categoryCells: List<BaseCell>
) : CategorySharesCell