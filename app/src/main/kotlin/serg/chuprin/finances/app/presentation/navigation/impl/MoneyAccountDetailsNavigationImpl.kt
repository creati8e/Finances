package serg.chuprin.finances.app.presentation.navigation.impl

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.core.api.presentation.navigation.MoneyAccountDetailsNavigation
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.toBundle
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

}