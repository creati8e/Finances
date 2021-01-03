package serg.chuprin.finances.core.api.presentation.navigation

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionsReportScreenArguments

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
interface DashboardNavigation {

    fun navigateToTransactionsReport(
        navController: NavController,
        screenArguments: TransactionsReportScreenArguments,
        sharedElementView: View
    )

    fun navigateToMoneyAccountCreation(navController: NavController, vararg sharedElementView: View)

    fun navigateToMoneyAccountsList(navController: NavController, vararg sharedElementView: View)

    fun navigateToMoneyAccountDetails(
        navController: NavController,
        moneyAccountId: Id,
        transitionName: String,
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