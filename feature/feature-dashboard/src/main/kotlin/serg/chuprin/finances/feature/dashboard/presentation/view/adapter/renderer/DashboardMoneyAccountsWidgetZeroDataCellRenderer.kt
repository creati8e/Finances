package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer

import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardMoneyAccountWidgetZeroDataCell

/**
 * Created by Sergey Chuprin on 22.04.2020.
 */
class DashboardMoneyAccountsWidgetZeroDataCellRenderer :
    ContainerRenderer<DashboardMoneyAccountWidgetZeroDataCell>() {
    override val type: Int = R.layout.cell_dashboard_money_accounts_widget_zero_data
}