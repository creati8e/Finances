package serg.chuprin.finances.core.api.presentation.navigation

import androidx.navigation.NavController
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
interface TransactionNavigation {

    fun navigateToCategoryPicker(
        navController: NavController,
        screenArguments: CategoriesListScreenArguments
    )

}