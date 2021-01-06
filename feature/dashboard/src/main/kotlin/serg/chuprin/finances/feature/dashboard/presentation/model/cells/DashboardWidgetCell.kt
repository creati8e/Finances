package serg.chuprin.finances.feature.dashboard.presentation.model.cells

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategorySharesCell
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
sealed class DashboardWidgetCell(
    open val widget: DashboardWidget
) : DiffCell<DashboardWidgetType> {

    data class MoneyAccounts(
        val isExpanded: Boolean,
        val cells: List<BaseCell>,
        override val widget: DashboardWidget.MoneyAccounts
    ) : DashboardWidgetCell(widget)

    data class Balance(
        val balance: String,
        val expensesAmount: String,
        val incomesAmount: String,
        override val widget: DashboardWidget.Balance
    ) : DashboardWidgetCell(widget)

    data class PeriodSelector(
        val currentPeriod: String,
        override val widget: DashboardWidget.PeriodSelector
    ) : DashboardWidgetCell(widget)

    data class RecentTransactions(
        val cells: List<BaseCell>,
        val showMoreTransactionsButtonIsVisible: Boolean,
        override val widget: DashboardWidget.RecentTransactions
    ) : DashboardWidgetCell(widget)

    data class Categories(
        val pageCells: List<CategorySharesCell>,
        override val widget: DashboardWidget.Categories
    ) : DashboardWidgetCell(widget)

    override val diffCellId: DashboardWidgetType
        get() = widget.type

}