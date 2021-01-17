package serg.chuprin.finances.app.presentation.navigation.impl

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.app.R
import serg.chuprin.finances.app.presentation.navigation.extensions.buildExtrasForSharedElements
import serg.chuprin.finances.feature.userprofile.presentation.UserProfileNavigation
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.toBundle
import serg.chuprin.finances.feature.categories.list.presentation.view.CategoriesListFragment
import serg.chuprin.finances.feature.userprofile.presentation.view.UserProfileFragmentDirections.navigateFromUserProfileToCategoriesList
import serg.chuprin.finances.feature.userprofile.presentation.view.UserProfileFragmentDirections.navigateFromUserProfileToDashboardWidgetsSetup

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
class UserProfileNavigationImpl : UserProfileNavigation {

    override fun navigateToDashboardWidgetsSetup(
        navController: NavController,
        vararg sharedElementView: View
    ) {
        navController.navigate(
            navigateFromUserProfileToDashboardWidgetsSetup(),
            buildExtrasForSharedElements(sharedElementView)
        )
    }

    override fun navigateToCategoriesList(
        navController: NavController,
        arguments: CategoriesListScreenArguments
    ) {
        navController.navigate(
            navigateFromUserProfileToCategoriesList().actionId,
            arguments.toBundle<CategoriesListFragment>(),
        )
    }

    override fun navigateToUnauthorizedGraph(rootNavigationController: NavController) {
        rootNavigationController.navigate(R.id.navigateToUnauthorizedGraph)
    }

}