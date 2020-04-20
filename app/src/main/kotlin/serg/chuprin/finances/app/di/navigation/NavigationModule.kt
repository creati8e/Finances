package serg.chuprin.finances.app.di.navigation

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.provider.CoreNavigationProvider
import serg.chuprin.finances.core.api.presentation.navigation.AuthorizationNavigation
import serg.chuprin.finances.core.api.presentation.navigation.OnboardingNavigation
import serg.chuprin.finances.feature.dashboard.R as DashboardR
import serg.chuprin.finances.feature.onboarding.R as OnboardingR

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@Module
object NavigationModule : CoreNavigationProvider {

    @Provides
    override fun onboardingNavigation(): OnboardingNavigation {
        return object : OnboardingNavigation {

            override fun navigateToDashboard(navController: NavController) {
                navController.setGraph(DashboardR.navigation.navigation_dashboard)
            }

        }
    }

    @Provides
    override fun authorizationNavigation(): AuthorizationNavigation {
        return object : AuthorizationNavigation {

            override fun navigateToDashboard(navController: NavController) {
                navController.setGraph(DashboardR.navigation.navigation_dashboard)
            }

            override fun navigateToOnboarding(navController: NavController) {
                navController.setGraph(OnboardingR.navigation.navigation_onboarding)
            }
        }
    }

}