package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.DataPeriodTypePopupMenuCell
import serg.chuprin.finances.core.api.presentation.screen.arguments.*

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
sealed class DashboardEvent {

    class ShowPeriodTypesPopupMenu(
        val menuCells: List<DataPeriodTypePopupMenuCell>
    ) : DashboardEvent()

    class NavigateToMoneyAccountDetailsScreen(
        val screenArguments: MoneyAccountDetailsScreenArguments
    ) : DashboardEvent()

    class NavigateToTransactionsReportScreen(
        val arguments: TransactionsReportScreenArguments
    ) : DashboardEvent()

    class NavigateToTransactionScreen(
        val screenArguments: TransactionScreenArguments
    ) : DashboardEvent()

    class NavigateToMoneyAccountsListScreen(
        val screenArguments: MoneyAccountsListScreenArguments
    ) : DashboardEvent()

    class NavigateToMoneyAccountCreationScreen(
        val screenArguments: MoneyAccountScreenArguments
    ) : DashboardEvent()

}