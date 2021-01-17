package serg.chuprin.finances.feature.moneyaccount.details.presentation

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
interface MoneyAccountDetailsNavigation {

    fun navigateToTransaction(
        navController: NavController,
        arguments: TransactionScreenArguments,
        sharedElementView: View
    )

    fun navigateToMoneyAccount(
        navController: NavController,
        arguments: MoneyAccountScreenArguments,
        sharedElementView: View
    )

}