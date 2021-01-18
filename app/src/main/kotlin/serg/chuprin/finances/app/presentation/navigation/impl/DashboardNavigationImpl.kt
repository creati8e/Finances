package serg.chuprin.finances.app.presentation.navigation.impl

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.app.presentation.navigation.extensions.buildExtrasForSharedElements
import serg.chuprin.finances.app.presentation.navigation.extensions.toNavigatorExtras
import serg.chuprin.finances.core.api.presentation.screen.arguments.*
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.toBundle
import serg.chuprin.finances.feature.dashboard.presentation.DashboardNavigation
import serg.chuprin.finances.feature.dashboard.presentation.view.DashboardFragmentDirections.*
import serg.chuprin.finances.feature.moneyaccount.presentation.view.MoneyAccountFragment
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.MoneyAccountDetailsFragment
import serg.chuprin.finances.feature.moneyaccounts.presentation.view.MoneyAccountsListFragment
import serg.chuprin.finances.feature.transaction.presentation.view.TransactionFragment
import serg.chuprin.finances.feature.transactions.presentation.view.TransactionsReportFragment

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
class DashboardNavigationImpl : DashboardNavigation {

    override fun navigateToTransaction(
        navController: NavController,
        arguments: TransactionScreenArguments,
        sharedElementView: View
    ) {
        navController.navigate(
            navigateFromDashboardToTransaction().actionId,
            arguments.toBundle<TransactionFragment>(),
            null,
            sharedElementView.toNavigatorExtras()
        )
    }

    override fun navigateToTransactionsReport(
        navController: NavController,
        screenArguments: TransactionsReportScreenArguments,
        sharedElementView: View
    ) {
        navController.navigate(
            navigateFromDashboardToTransactionsReport().actionId,
            screenArguments.toBundle<TransactionsReportFragment>(),
            null,
            sharedElementView.toNavigatorExtras()
        )
    }

    override fun navigateToMoneyAccount(
        navController: NavController,
        screenArguments: MoneyAccountScreenArguments,
        vararg sharedElementView: View
    ) {
        navController.navigate(
            navigateFromDashboardToMoneyAccount().actionId,
            screenArguments.toBundle<MoneyAccountFragment>(),
            null,
            buildExtrasForSharedElements(sharedElementView)
        )
    }

    override fun navigateToMoneyAccountsList(
        navController: NavController,
        screenArguments: MoneyAccountsListScreenArguments,
        sharedElementView: View
    ) {
        navController.navigate(
            navigateFromDashboardToMoneyAccountsList().actionId,
            screenArguments.toBundle<MoneyAccountsListFragment>(),
            null,
            sharedElementView.toNavigatorExtras()
        )
    }

    override fun navigateToMoneyAccountDetails(
        navController: NavController,
        screenArguments: MoneyAccountDetailsScreenArguments,
        vararg sharedElementView: View
    ) {
        navController.navigate(
            navigateFromDashboardToMoneyAccountDetails().actionId,
            screenArguments.toBundle<MoneyAccountDetailsFragment>(),
            null,
            buildExtrasForSharedElements(sharedElementView)
        )
    }

    override fun navigateToUserProfile(
        navController: NavController,
        vararg sharedElementView: View
    ) {
        navController.navigate(
            navigateFromDashboardToUserProfile().actionId,
            null,
            null,
            buildExtrasForSharedElements(sharedElementView)
        )
    }

}