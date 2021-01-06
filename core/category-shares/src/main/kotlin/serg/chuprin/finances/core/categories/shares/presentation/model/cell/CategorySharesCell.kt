package serg.chuprin.finances.core.categories.shares.presentation.model.cell

import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.piechart.model.PieChartDataPart

/**
 * Created by Sergey Chuprin on 28.04.2020.
 */
interface CategorySharesCell : BaseCell {

    class ChangedPayload

    val label: String
    val totalAmount: String
    val chartParts: List<PieChartDataPart>
    val transactionType: PlainTransactionType
    val categoryCells: List<BaseCell>
}