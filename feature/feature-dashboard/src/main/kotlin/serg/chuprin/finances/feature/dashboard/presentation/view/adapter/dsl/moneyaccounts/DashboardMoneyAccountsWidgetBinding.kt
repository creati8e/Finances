package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.moneyaccounts

import kotlinx.android.synthetic.main.cell_dashboard_money_account.*
import kotlinx.android.synthetic.main.cell_widget_dashboard_money_accounts.*
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.context.RecyclerViewAdapterContext
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.moneyaccounts.DashboardMoneyAccountWidgetZeroDataCell
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardIntent
import serg.chuprin.finances.feature.dashboard.presentation.model.viewmodel.DashboardViewModel
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.moneyaccounts.animation.DashboardMoneyAccountsWidgetAnimationController
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.moneyaccounts.diff.DashboardMoneyAccountsDiffCallback

/**
 * Created by Sergey Chuprin on 29.04.2020.
 */
fun RecyclerViewAdapterContext.setupMoneyAccountsWidgetBinding(
    viewModel: DashboardViewModel
) {
    val animationController = DashboardMoneyAccountsWidgetAnimationController()

    add<DashboardWidgetCell.MoneyAccounts>(R.layout.cell_widget_dashboard_money_accounts) {
        nestedHorizontalList(
            { moneyAccountsRecyclerView },
            DashboardMoneyAccountsDiffCallback()
        ) {
            setupRecyclerView {
                moneyAccountsRecyclerView.setHasFixedSize(true)
            }
            add<DashboardMoneyAccountWidgetZeroDataCell>(
                R.layout.cell_dashboard_money_accounts_widget_zero_data
            )
            add<DashboardMoneyAccountCell>(R.layout.cell_dashboard_money_account) {
                bind { cell, payloads ->
                    if (payloads.isEmpty() || DashboardMoneyAccountCellChangedPayload in payloads) {
                        nameTextView.text = cell.name
                        balanceTextView.text = cell.balance
                        cardView.isActivated = cell.favoriteIconIsVisible
                        favoriteImageView.makeVisibleOrGone(cell.favoriteIconIsVisible)
                    }
                    cardView.tag = cell.transitionName
                    cardView.transitionName = cell.transitionName
                }
                setupViews {
                    setClickListener(
                        cardView,
                        { cell ->
                            viewModel.dispatchIntent(DashboardIntent.ClickOnMoneyAccount(cell))
                        }
                    )
                }
            }
        }
        bind { cell, payloads ->
            if (DashboardMoneyAccountsExpansionChangedPayload in payloads) {
                animationController.toggleExpansion(
                    cell.isExpanded,
                    expandableLayout,
                    expansionArrowImageView
                )
            } else {
                expansionArrowImageView.setImageResource(
                    if (cell.isExpanded) {
                        R.drawable.ic_collapse
                    } else {
                        R.drawable.ic_expand
                    }
                )
                expandableLayout.makeVisibleOrGone(cell.isExpanded)
            }
            if (payloads.isEmpty() || DashboardMoneyAccountCellsChangedPayload in payloads) {
                setNestedCells(cell.cells)
            }
        }
        setupViews {
            setClickListener(
                showAllAccountsButton,
                { viewModel.dispatchIntent(DashboardIntent.ClickOnMoneyAccountsListButton) }
            )
            setClickListener(
                moneyAccountsSubtitleLayout,
                { cell ->
                    viewModel.dispatchIntent(DashboardIntent.ToggleMoneyAccountsVisibility(cell))
                }
            )
        }
    }
}

/**
 * Represents particular money account cell change event.
 */
object DashboardMoneyAccountCellChangedPayload

/**
 * Represents widget part expansion change event.
 */
object DashboardMoneyAccountsExpansionChangedPayload

/**
 * Represents the whole money accounts list change event.
 */
object DashboardMoneyAccountCellsChangedPayload