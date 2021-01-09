package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts

import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_widget_dashboard_money_accounts.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onScrollStateChanged
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountWidgetZeroDataCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.animation.DashboardMoneyAccountsWidgetAnimationController
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.DashboardMoneyAccountCellsChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.DashboardMoneyAccountsDiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.DashboardMoneyAccountsExpansionChangedPayload

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class DashboardMoneyAccountsWidgetCellRenderer(
    private val clickOnCreateMoneyAccountButton: () -> Unit,
    private val clickOnShowMoneyAccountsListButton: () -> Unit,
    private val clickOnWidgetSubtitle: (adapterPosition: Int) -> Unit,
    private val clickOnMoneyAccountCell: (cell: DashboardMoneyAccountCell) -> Unit
) : ContainerRenderer<DashboardWidgetCell.MoneyAccounts>() {

    override val type: Int = R.layout.cell_widget_dashboard_money_accounts

    private var adapterState: Parcelable? = null

    private val animationController = DashboardMoneyAccountsWidgetAnimationController()
    private val moneyAccountCellsAdapter =
        DiffMultiViewAdapter(DashboardMoneyAccountsDiffCallback()).apply {
            registerRenderer<DashboardMoneyAccountWidgetZeroDataCell>(
                R.layout.cell_dashboard_money_accounts_widget_zero_data
            )
            registerRenderer(DashboardMoneyAccountCellRenderer(::handleOnMoneyAccountCellClick))
        }

    override fun bindView(viewHolder: ContainerHolder, cell: DashboardWidgetCell.MoneyAccounts) {
        bindMoneyAccountCells(viewHolder, cell)
        with(viewHolder) {
            expansionArrowImageView.setImageResource(
                if (cell.isExpanded) {
                    R.drawable.ic_collapse
                } else {
                    R.drawable.ic_expand
                }
            )
            expandableLayout.makeVisibleOrGone(cell.isExpanded)
        }
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: DashboardWidgetCell.MoneyAccounts,
        payloads: MutableList<Any>
    ) {
        if (DashboardMoneyAccountsExpansionChangedPayload in payloads) {
            with(viewHolder) {
                animationController.toggleExpansion(
                    cell.isExpanded,
                    expandableLayout,
                    expansionArrowImageView
                )
            }
        }
        if (DashboardMoneyAccountCellsChangedPayload in payloads) {
            bindMoneyAccountCells(viewHolder, cell)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            with(moneyAccountsRecyclerView) {
                adapter = moneyAccountCellsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                onScrollStateChanged { recyclerView, newState ->
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        adapterState = recyclerView.layoutManager!!.onSaveInstanceState()
                    }
                }
            }
            addAccountButton.onClick(clickOnCreateMoneyAccountButton)
            showAllAccountsButton.onClick(clickOnShowMoneyAccountsListButton)
            moneyAccountsSubtitleLayout.onClick { clickOnWidgetSubtitle(adapterPosition) }
        }
    }

    private fun bindMoneyAccountCells(
        viewHolder: ContainerHolder,
        cell: DashboardWidgetCell.MoneyAccounts
    ) {
        moneyAccountCellsAdapter.setItems(cell.cells) {
            if (adapterState != null) {
                viewHolder.moneyAccountsRecyclerView.layoutManager?.onRestoreInstanceState(adapterState)
            }
        }
    }

    private fun handleOnMoneyAccountCellClick(adapterPosition: Int) {
        val cellOrNull = moneyAccountCellsAdapter.getItemOrNull(adapterPosition)
        val cell = cellOrNull as? DashboardMoneyAccountCell ?: return
        clickOnMoneyAccountCell(cell)
    }

}