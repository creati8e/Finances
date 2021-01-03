package serg.chuprin.finances.app.di.navigation

import androidx.navigation.NavController
import serg.chuprin.finances.core.api.presentation.navigation.TransactionNavigation
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.toBundle
import serg.chuprin.finances.feature.categories.impl.presentation.view.CategoriesListFragment
import serg.chuprin.finances.feature.transaction.presentation.view.TransactionFragmentDirections.navigateFromTransactionToCategoriesList

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
class TransactionNavigationImpl : TransactionNavigation {

    override fun navigateToCategoryPicker(
        navController: NavController,
        screenArguments: CategoriesListScreenArguments
    ) {
        navController.navigate(
            navigateFromTransactionToCategoriesList().actionId,
            screenArguments.toBundle<CategoriesListFragment>()
        )
    }

}