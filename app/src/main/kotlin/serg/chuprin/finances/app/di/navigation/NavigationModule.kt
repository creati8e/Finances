package serg.chuprin.finances.app.di.navigation

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.provider.CoreNavigationProvider
import serg.chuprin.finances.core.api.presentation.navigation.AuthorizationNavigation
import serg.chuprin.finances.feature.dashboard.R

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@Module
class NavigationModule : CoreNavigationProvider {

    @Provides
    override fun authorizationNavigation(): AuthorizationNavigation {
        return object : AuthorizationNavigation {
            override fun navigateToDashboard(navController: NavController) {
                navController.setGraph(R.navigation.navigation_dashboard)
            }
        }
    }

}