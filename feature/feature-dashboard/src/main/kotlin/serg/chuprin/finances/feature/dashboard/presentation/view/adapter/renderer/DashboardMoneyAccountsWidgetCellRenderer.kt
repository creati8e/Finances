package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cell_widget_dashboard_money_accounts.*
import serg.chuprin.adapter.*
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardMoneyAccountCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell

/**
 * Created by Sergey Chuprin on 21.04.2020.
 */
class DashboardMoneyAccountsWidgetCellRenderer :
    ContainerRenderer<DashboardWidgetCell.MoneyAccounts>() {

    override val type: Int = R.layout.cell_widget_dashboard_money_accounts

    private val moneyAccountCellsAdapter =
        DiffMultiViewAdapter(DiffCallback<DashboardMoneyAccountCell>()).apply {
            registerRenderer(DashboardMoneyAccountCellRenderer())
        }

    // TODO: Add payload.
    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.MoneyAccounts) {
        moneyAccountCellsAdapter.setItems(model.cells)
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder.recyclerView) {
            adapter = moneyAccountCellsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}