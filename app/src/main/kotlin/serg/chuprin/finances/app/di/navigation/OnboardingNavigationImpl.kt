package serg.chuprin.finances.app.di.navigation

import androidx.navigation.NavController
import serg.chuprin.finances.app.AuthorizedGraphDirections.navigateFromOnboardingToDashboard
import serg.chuprin.finances.core.api.presentation.navigation.OnboardingNavigation

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
class OnboardingNavigationImpl : OnboardingNavigation {

    override fun navigateToDashboard(navController: NavController) {
        navController.navigate(navigateFromOnboardingToDashboard())
    }

}