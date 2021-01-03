package serg.chuprin.finances.app.di.navigation

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.presentation.navigation.DashboardNavigation
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionsReportScreenArguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.toBundle
import serg.chuprin.finances.feature.dashboard.presentation.view.DashboardFragmentDirections.*
import serg.chuprin.finances.feature.moneyaccount.details.presentation.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.MoneyAccountDetailsFragment
import serg.chuprin.finances.feature.transactions.presentation.view.TransactionsReportFragment

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
class DashboardNavigationImpl : DashboardNavigation {

    override fun navigateToTransaction(navController: NavController) {
        navController.navigate(navigateFromDashboardToTransaction())
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

    override fun navigateToMoneyAccountCreation(
        navController: NavController,
        vararg sharedElementView: View
    ) {
        navController.navigate(
            navigateFromDashboardToMoneyAccountCreation(),
            buildExtrasForSharedElements(sharedElementView)
        )
    }

    override fun navigateToMoneyAccountsList(
        navController: NavController,
        vararg sharedElementView: View
    ) {
        navController.navigate(
            navigateFromDashboardToMoneyAccountsList(),
            buildExtrasForSharedElements(sharedElementView)
        )
    }

    override fun navigateToMoneyAccountDetails(
        navController: NavController,
        moneyAccountId: Id,
        transitionName: String,
        vararg sharedElementView: View
    ) {
        navController.navigate(
            navigateFromDashboardToMoneyAccountDetails().actionId,
            MoneyAccountDetailsScreenArguments(moneyAccountId, transitionName)
                .toBundle<MoneyAccountDetailsFragment>(),
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