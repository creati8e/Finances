package serg.chuprin.finances.feature.dashboard.presentation.view.adapter

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardIntent
import serg.chuprin.finances.feature.dashboard.presentation.model.viewmodel.DashboardViewModel
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.DashboardCategoriesWidgetCellRenderer
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.DashboardAdapterDiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.header.DashboardHeaderWidgetCellRenderer
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.DashboardMoneyAccountsWidgetCellRenderer
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.transactions.DashboardRecentTransactionsWidgetCellRenderer

/**
 * Created by Sergey Chuprin on 29.04.2020.
 */
fun dashboard(viewModel: DashboardViewModel): DiffMultiViewAdapter<BaseCell> {
    return DiffMultiViewAdapter(DashboardAdapterDiffCallback()).apply {
        registerRenderer(
            DashboardHeaderWidgetCellRenderer(
                clickOnCurrentPeriod = {
                    viewModel.dispatchIntent(DashboardIntent.ClickOnCurrentPeriod)
                },
                clickOnNextPeriod = {
                    viewModel.dispatchIntent(DashboardIntent.ClickOnNextPeriodButton)
                },
                clickOnPreviousPeriod = {
                    viewModel.dispatchIntent(DashboardIntent.ClickOnPreviousPeriodButton)
                },
                clickOnCurrentPeriodIncomes = {
                    viewModel.dispatchIntent(DashboardIntent.ClickOnCurrentPeriodIncomesButton)
                },
                clickOnRestoreDefaultPeriod = {
                    viewModel.dispatchIntent(DashboardIntent.ClickOnRestoreDefaultPeriodButton)
                },
                clickOnCurrentPeriodExpenses = {
                    viewModel.dispatchIntent(DashboardIntent.ClickOnCurrentPeriodExpensesButton)
                }
            )
        )
        registerRenderer(
            DashboardCategoriesWidgetCellRenderer(
                onCategoryClicked = { categoryChipCell ->
                    viewModel.dispatchIntent(DashboardIntent.ClickOnCategory(categoryChipCell))
                }
            )
        )
        registerRenderer(
            DashboardMoneyAccountsWidgetCellRenderer(
                clickOnMoneyAccountCell = { cell ->
                    viewModel.dispatchIntent(DashboardIntent.ClickOnMoneyAccount(cell))
                },
                clickOnWidgetSubtitle = { adapterPosition ->
                    val itemOrNull = getItemOrNull(adapterPosition)
                    val cell = itemOrNull as? DashboardWidgetCell.MoneyAccounts
                        ?: return@DashboardMoneyAccountsWidgetCellRenderer
                    viewModel.dispatchIntent(DashboardIntent.ToggleMoneyAccountsVisibility(cell))
                },
                clickOnCreateMoneyAccountButton = {
                    viewModel.dispatchIntent(DashboardIntent.ClickOnCreateMoneyAccountButton)
                },
                clickOnShowMoneyAccountsListButton = {
                    viewModel.dispatchIntent(DashboardIntent.ClickOnMoneyAccountsListButton)
                }
            )
        )
        registerRenderer(
            DashboardRecentTransactionsWidgetCellRenderer(
                clickOnShowMoreTransactions = {
                    viewModel.dispatchIntent(DashboardIntent.ClickOnShowMoreTransactionsButton)
                }
            )
        )
    }
}