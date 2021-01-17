package serg.chuprin.finances.feature.userprofile.presentation

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
interface UserProfileNavigation {

    fun navigateToDashboardWidgetsSetup(
        navController: NavController,
        vararg sharedElementView: View
    )

    fun navigateToCategoriesList(
        navController: NavController,
        arguments: CategoriesListScreenArguments
    )

    fun navigateToUnauthorizedGraph(rootNavigationController: NavController)

}