package serg.chuprin.finances.app.di.navigation

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.app.AuthorizedGraphDirections
import serg.chuprin.finances.app.NotAuthorizedGraphDirections
import serg.chuprin.finances.core.api.di.provider.CoreNavigationProvider
import serg.chuprin.finances.core.api.presentation.navigation.AuthorizationNavigation
import serg.chuprin.finances.core.api.presentation.navigation.DashboardNavigation
import serg.chuprin.finances.core.api.presentation.navigation.OnboardingNavigation

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@Module
object NavigationModule : CoreNavigationProvider {

    @get:Provides
    override val dashboardNavigation: DashboardNavigation
        get() {
            return object : DashboardNavigation {

                override fun navigateToMoneyAccountsList(navController: NavController) {
                    AuthorizedGraphDirections.navigateFromDashboardToMoneyAccountsList().run {
                        navController.navigate(this)
                    }
                }

            }
        }

    @get:Provides
    override val onboardingNavigation: OnboardingNavigation
        get() {
            return object : OnboardingNavigation {

                override fun navigateToDashboard(navController: NavController) {
                    AuthorizedGraphDirections.navigateFromOnboardingToDashboard().run {
                        navController.navigate(this)
                    }
                }

            }
        }

    @get:Provides
    override val authorizationNavigation: AuthorizationNavigation
        get() {
            return object : AuthorizationNavigation {

                override fun navigateToAuthorizedGraph(navController: NavController) {
                    NotAuthorizedGraphDirections.navigateFromAuthorizationToAuthorizedGraph().run {
                        navController.navigate(this)
                    }
                }

            }
        }

}