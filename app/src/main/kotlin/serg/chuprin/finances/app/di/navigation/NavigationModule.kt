package serg.chuprin.finances.app.di.navigation

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.app.AuthorizedGraphDirections
import serg.chuprin.finances.app.NotAuthorizedGraphDirections
import serg.chuprin.finances.core.api.di.provider.CoreNavigationProvider
import serg.chuprin.finances.core.api.presentation.navigation.AuthorizationNavigation
import serg.chuprin.finances.core.api.presentation.navigation.OnboardingNavigation

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@Module
object NavigationModule : CoreNavigationProvider {

    @Provides
    override fun onboardingNavigation(): OnboardingNavigation {
        return object : OnboardingNavigation {

            override fun navigateToDashboard(navController: NavController) {
                AuthorizedGraphDirections.navigateFromOnboardingToDashboard().run {
                    navController.navigate(this)
                }
            }

        }
    }

    @Provides
    override fun authorizationNavigation(): AuthorizationNavigation {
        return object : AuthorizationNavigation {

            override fun navigateToAuthorizedGraph(navController: NavController) {
                NotAuthorizedGraphDirections.navigateFromAuthorizationToAuthorizedGraph().run {
                    navController.navigate(this)
                }
            }

        }
    }

}