package serg.chuprin.finances.app.presentation.navigation.impl

import android.view.View
import androidx.navigation.NavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import serg.chuprin.finances.app.presentation.navigation.extensions.buildExtrasForSharedElements
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.toBundle
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.MoneyAccountDetailsFragment
import serg.chuprin.finances.feature.moneyaccount.presentation.view.MoneyAccountFragment
import serg.chuprin.finances.feature.moneyaccounts.presentation.MoneyAccountsListNavigation
import serg.chuprin.finances.feature.moneyaccounts.presentation.view.MoneyAccountsListFragmentDirections.navigateFromMoneyAccountsListToMoneyAccount
import serg.chuprin.finances.feature.moneyaccounts.presentation.view.MoneyAccountsListFragmentDirections.navigateFromMoneyAccountsListToMoneyAccountDetails

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
class MoneyAccountsListNavigationImpl : MoneyAccountsListNavigation {

    override fun navigateToMoneyAccount(
        navController: NavController,
        screenArguments: MoneyAccountScreenArguments,
        vararg sharedElementView: FloatingActionButton
    ) {
        navController.navigate(
            navigateFromMoneyAccountsListToMoneyAccount().actionId,
            screenArguments.toBundle<MoneyAccountFragment>(),
            null,
            buildExtrasForSharedElements(sharedElementView)
        )
    }

    override fun navigateToMoneyAccountDetails(
        navController: NavController,
        screenArguments: MoneyAccountDetailsScreenArguments,
        vararg sharedElementViews: View
    ) {
        navController.navigate(
            navigateFromMoneyAccountsListToMoneyAccountDetails().actionId,
            screenArguments.toBundle<MoneyAccountDetailsFragment>(),
            null,
            buildExtrasForSharedElements(sharedElementViews)
        )
    }

}