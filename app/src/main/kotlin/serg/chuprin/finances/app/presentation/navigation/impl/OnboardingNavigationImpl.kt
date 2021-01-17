package serg.chuprin.finances.app.presentation.navigation.impl

import androidx.navigation.NavController
import serg.chuprin.finances.app.AuthorizedGraphDirections.navigateFromOnboardingToDashboard
import serg.chuprin.finances.feature.onboarding.presentation.OnboardingNavigation

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
class OnboardingNavigationImpl : OnboardingNavigation {

    override fun navigateToDashboard(navController: NavController) {
        navController.navigate(navigateFromOnboardingToDashboard())
    }

}