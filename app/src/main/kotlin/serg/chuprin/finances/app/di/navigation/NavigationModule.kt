package serg.chuprin.finances.app.di.navigation

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.app.R
import serg.chuprin.finances.core.api.di.provider.CoreNavigationProvider
import serg.chuprin.finances.core.api.presentation.navigation.*

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@Module
object NavigationModule : CoreNavigationProvider {

    @get:Provides
    override val dashboardNavigation: DashboardNavigation
        get() = DashboardNavigationImpl()

    @get:Provides
    override val userProfileNavigation: UserProfileNavigation
        get() = UserProfileNavigationImpl()

    @get:Provides
    override val moneyAccountsListNavigation: MoneyAccountsListNavigation
        get() = MoneyAccountsListNavigationImpl()

    @get:Provides
    override val onboardingNavigation: OnboardingNavigation
        get() = OnboardingNavigationImpl()

    @get:Provides
    override val authorizationNavigation: AuthorizationNavigation
        get() {
            return object : AuthorizationNavigation {

                override fun navigateToAuthorizedGraph(navController: NavController) {
                    navController.navigate(R.id.navigateToAuthorizedGraph)
                }

            }
        }

}