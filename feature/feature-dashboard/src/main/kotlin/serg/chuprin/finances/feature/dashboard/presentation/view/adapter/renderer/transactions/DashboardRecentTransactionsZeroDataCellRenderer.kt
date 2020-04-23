package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer.transactions

import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions.DashboardRecentTransactionsZeroDataCell

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardRecentTransactionsZeroDataCellRenderer :
    ContainerRenderer<DashboardRecentTransactionsZeroDataCell>() {
    override val type: Int = R.layout.cell_dashboard_recent_transactions_zero_data
}