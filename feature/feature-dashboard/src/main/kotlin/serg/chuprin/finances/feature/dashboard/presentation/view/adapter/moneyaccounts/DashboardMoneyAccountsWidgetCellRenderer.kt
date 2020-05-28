package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cell_widget_dashboard_money_accounts.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
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
) :
    ContainerRenderer<DashboardWidgetCell.MoneyAccounts>() {

    override val type: Int = R.layout.cell_widget_dashboard_money_accounts

    private val animationController = DashboardMoneyAccountsWidgetAnimationController()
    private val moneyAccountCellsAdapter =
        DiffMultiViewAdapter(DashboardMoneyAccountsDiffCallback()).apply {
            registerRenderer<DashboardMoneyAccountWidgetZeroDataCell>(
                R.layout.cell_dashboard_money_accounts_widget_zero_data
            )
            registerRenderer(DashboardMoneyAccountCellRenderer(::handleOnMoneyAccountCellClick))
        }

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.MoneyAccounts) {
        moneyAccountCellsAdapter.setItems(model.cells)
        with(holder) {
            expansionArrowImageView.setImageResource(
                if (model.isExpanded) {
                    R.drawable.ic_collapse
                } else {
                    R.drawable.ic_expand
                }
            )
            expandableLayout.makeVisibleOrGone(model.isExpanded)
        }
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardWidgetCell.MoneyAccounts,
        payloads: MutableList<Any>
    ) {
        if (DashboardMoneyAccountsExpansionChangedPayload in payloads) {
            with(holder) {
                animationController.toggleExpansion(
                    model.isExpanded,
                    expandableLayout,
                    expansionArrowImageView
                )
            }
        }
        if (DashboardMoneyAccountCellsChangedPayload in payloads) {
            moneyAccountCellsAdapter.setItems(model.cells)
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            with(moneyAccountsRecyclerView) {
                adapter = moneyAccountCellsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            addAccountButton.onClick(clickOnCreateMoneyAccountButton)
            showAllAccountsButton.onClick(clickOnShowMoneyAccountsListButton)
            moneyAccountsSubtitleLayout.onClick { clickOnWidgetSubtitle(adapterPosition) }
        }
    }

    private fun handleOnMoneyAccountCellClick(adapterPosition: Int) {
        val cellOrNull = moneyAccountCellsAdapter.getItemOrNull(adapterPosition)
        val cell = cellOrNull as? DashboardMoneyAccountCell ?: return
        clickOnMoneyAccountCell(cell)
    }

}