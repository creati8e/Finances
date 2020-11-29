package serg.chuprin.finances.core.api.presentation.navigation

import android.view.View
import androidx.navigation.NavController

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
interface UserProfileNavigation {

    fun navigateToDashboardWidgetsSetup(
        navController: NavController,
        vararg sharedElementView: View
    )

    fun navigateToUnauthorizedGraph(rootNavigationController: NavController)

}