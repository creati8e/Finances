package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.renderer

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.cell_widget_dashboard_money_accounts.*
import serg.chuprin.adapter.*
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.DashboardMoneyAccountsDiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.payload.DashboardMoneyAccountCellsChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.diff.payload.DashboardMoneyAccountsExpansionChangedPayload
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.renderer.animation.DashboardMoneyAccountsWidgetAnimationController


/**
 * Created by Sergey Chuprin on 21.04.2020.
 */
class DashboardMoneyAccountsWidgetCellRenderer :
    ContainerRenderer<DashboardWidgetCell.MoneyAccounts>() {

    override val type: Int = R.layout.cell_widget_dashboard_money_accounts

    private val animationController = DashboardMoneyAccountsWidgetAnimationController()

    private val moneyAccountCellsAdapter =
        DiffMultiViewAdapter(DashboardMoneyAccountsDiffCallback()).apply {
            registerRenderer(DashboardMoneyAccountCellRenderer())
            registerRenderer(DashboardMoneyAccountsWidgetZeroDataCellRenderer())
        }

    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.MoneyAccounts) {
        moneyAccountCellsAdapter.setItems(model.cells)
        holder.expansionArrowImageView.setImageResource(
            if (model.isExpanded) R.drawable.ic_collapse else R.drawable.ic_expand
        )
        holder.expandableLayout.makeVisibleOrGone(model.isExpanded)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardWidgetCell.MoneyAccounts,
        payloads: MutableList<Any>
    ) {
        if (DashboardMoneyAccountsExpansionChangedPayload in payloads) {
            animationController.toggleExpansion(
                model.isExpanded,
                holder.expandableLayout,
                holder.expansionArrowImageView
            )
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
            subtitleLayout.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
        with(holder.recyclerView) {
            adapter = moneyAccountCellsAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}