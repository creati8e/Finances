package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts

import kotlinx.android.synthetic.main.cell_dashboard_money_account.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.DashboardMoneyAccountCellChangedPayload

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class DashboardMoneyAccountCellRenderer(
    private val onCellClicked: (adapterPosition: Int) -> Unit
) : ContainerRenderer<DashboardMoneyAccountCell>() {

    override val type: Int = R.layout.cell_dashboard_money_account

    override fun bindView(viewHolder: ContainerHolder, cell: DashboardMoneyAccountCell) {
        bindData(viewHolder, cell)
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: DashboardMoneyAccountCell,
        payloads: MutableList<Any>
    ) {
        if (DashboardMoneyAccountCellChangedPayload in payloads) {
            bindData(viewHolder, cell)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            cardView.onClick {
                onCellClicked(adapterPosition)
            }
        }
    }

    private fun bindData(viewHolder: ContainerHolder, cell: DashboardMoneyAccountCell) {
        with(viewHolder) {
            cardView.tag = cell.transitionName
            cardView.transitionName = cell.transitionName

            nameTextView.text = cell.name
            balanceTextView.text = cell.balance
            cardView.isActivated = cell.favoriteIconIsVisible
            favoriteImageView.makeVisibleOrGone(cell.favoriteIconIsVisible)
        }
    }

}
