package serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page

import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.piechart.model.PieChartDataPart

/**
 * Created by Sergey Chuprin on 28.04.2020.
 */
interface DashboardCategoriesPageCell : BaseCell {
    val label: String
    val totalAmount: String
    val chartParts: List<PieChartDataPart>
    val transactionType: PlainTransactionType
    val categoryCells: List<BaseCell>
}