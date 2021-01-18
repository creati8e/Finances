package serg.chuprin.finances.app.presentation.navigation.impl

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.app.presentation.navigation.extensions.toNavigatorExtras
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.toBundle
import serg.chuprin.finances.feature.moneyaccount.presentation.view.MoneyAccountFragment
import serg.chuprin.finances.feature.moneyaccount.details.presentation.MoneyAccountDetailsNavigation
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.MoneyAccountDetailsFragmentDirections.navigateFromMoneyAccountDetailsToMoneyAccountEditing
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.MoneyAccountDetailsFragmentDirections.navigateFromMoneyAccountDetailsToTransaction
import serg.chuprin.finances.feature.transaction.presentation.view.TransactionFragment

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
class MoneyAccountDetailsNavigationImpl : MoneyAccountDetailsNavigation {

    override fun navigateToTransaction(
        navController: NavController,
        arguments: TransactionScreenArguments,
        sharedElementView: View
    ) {
        navController.navigate(
            navigateFromMoneyAccountDetailsToTransaction().actionId,
            arguments.toBundle<TransactionFragment>(),
            null,
            sharedElementView.toNavigatorExtras()
        )
    }

    override fun navigateToMoneyAccount(
        navController: NavController,
        arguments: MoneyAccountScreenArguments,
        sharedElementView: View
    ) {
        navController.navigate(
            navigateFromMoneyAccountDetailsToMoneyAccountEditing().actionId,
            arguments.toBundle<MoneyAccountFragment>(),
            null,
            sharedElementView.toNavigatorExtras()
        )
    }

}