package serg.chuprin.finances.feature.transaction.presentation

import androidx.navigation.NavController
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
interface TransactionNavigation {

    fun navigateToCategoryPicker(
        navController: NavController,
        screenArguments: CategoriesListScreenArguments
    )

    fun navigateToMoneyAccountPicker(
        navController: NavController,
        screenArguments: MoneyAccountsListScreenArguments
    )

}