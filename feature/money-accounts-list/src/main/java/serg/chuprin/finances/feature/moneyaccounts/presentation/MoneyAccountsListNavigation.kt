package serg.chuprin.finances.feature.moneyaccounts.presentation

import android.view.View
import androidx.navigation.NavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
interface MoneyAccountsListNavigation {

    fun navigateToMoneyAccount(
        navController: NavController,
        screenArguments: MoneyAccountScreenArguments,
        vararg sharedElementView: FloatingActionButton
    )

    fun navigateToMoneyAccountDetails(
        navController: NavController,
        screenArguments: MoneyAccountDetailsScreenArguments,
        vararg sharedElementViews: View
    )

}