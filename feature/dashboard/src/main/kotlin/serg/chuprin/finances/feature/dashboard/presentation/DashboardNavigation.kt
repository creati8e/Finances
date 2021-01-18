package serg.chuprin.finances.feature.dashboard.presentation

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.core.api.presentation.screen.arguments.*

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
interface DashboardNavigation {

    fun navigateToTransactionsReport(
        navController: NavController,
        screenArguments: TransactionsReportScreenArguments,
        sharedElementView: View
    )

    fun navigateToMoneyAccount(
        navController: NavController,
        screenArguments: MoneyAccountScreenArguments,
        vararg sharedElementView: View
    )

    fun navigateToMoneyAccountsList(
        navController: NavController,
        screenArguments: MoneyAccountsListScreenArguments,
        sharedElementView: View
    )

    fun navigateToMoneyAccountDetails(
        navController: NavController,
        screenArguments: MoneyAccountDetailsScreenArguments,
        vararg sharedElementView: View
    )

    fun navigateToUserProfile(
        navController: NavController,
        vararg sharedElementView: View
    )

    fun navigateToTransaction(
        navController: NavController,
        arguments: TransactionScreenArguments,
        sharedElementView: View
    )

}