package serg.chuprin.finances.app.di.navigation

import android.view.View
import androidx.navigation.NavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.presentation.navigation.MoneyAccountsListNavigation
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.toBundle
import serg.chuprin.finances.feature.moneyaccount.details.presentation.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.MoneyAccountDetailsFragment
import serg.chuprin.finances.feature.moneyaccounts.presentation.view.MoneyAccountsListFragmentDirections.navigateFromMoneyAccountsListToMoneyAccountCreation
import serg.chuprin.finances.feature.moneyaccounts.presentation.view.MoneyAccountsListFragmentDirections.navigateFromMoneyAccountsListToMoneyAccountDetails

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
class MoneyAccountsListNavigationImpl : MoneyAccountsListNavigation {

    override fun navigateToMoneyAccountCreation(
        navController: NavController,
        vararg sharedElementView: FloatingActionButton
    ) {
        navController.navigate(
            navigateFromMoneyAccountsListToMoneyAccountCreation(),
            buildExtrasForSharedElements(sharedElementView)
        )
    }

    override fun navigateToMoneyAccountDetails(
        navController: NavController,
        moneyAccountId: Id,
        transitionName: String,
        vararg sharedElementViews: View
    ) {
        navController.navigate(
            navigateFromMoneyAccountsListToMoneyAccountDetails().actionId,
            MoneyAccountDetailsScreenArguments(moneyAccountId, transitionName)
                .toBundle<MoneyAccountDetailsFragment>(),
            null,
            buildExtrasForSharedElements(sharedElementViews)
        )
    }

}