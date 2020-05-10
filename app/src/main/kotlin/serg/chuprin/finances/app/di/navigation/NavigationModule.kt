package serg.chuprin.finances.app.di.navigation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.app.AuthorizedGraphDirections
import serg.chuprin.finances.app.NotAuthorizedGraphDirections
import serg.chuprin.finances.app.R
import serg.chuprin.finances.core.api.di.provider.CoreNavigationProvider
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.presentation.extensions.toBundle
import serg.chuprin.finances.core.api.presentation.navigation.AuthorizationNavigation
import serg.chuprin.finances.core.api.presentation.navigation.DashboardNavigation
import serg.chuprin.finances.core.api.presentation.navigation.MoneyAccountsListNavigation
import serg.chuprin.finances.core.api.presentation.navigation.OnboardingNavigation
import serg.chuprin.finances.feature.dashboard.presentation.view.DashboardFragmentDirections
import serg.chuprin.finances.feature.moneyaccount.details.presentation.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.MoneyAccountDetailsFragment
import serg.chuprin.finances.feature.moneyaccounts.presentation.view.MoneyAccountsListFragmentDirections

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@Module
object NavigationModule : CoreNavigationProvider {

    @get:Provides
    override val dashboardNavigation: DashboardNavigation
        get() {
            return object : DashboardNavigation {

                override fun navigateToMoneyAccountCreation(
                    navController: NavController,
                    vararg sharedElementView: View
                ) {
                    DashboardFragmentDirections.navigateFromDashboardToMoneyAccountCreation().run {
                        navController.navigate(
                            this,
                            buildExtrasForSharedElements(sharedElementView)
                        )
                    }
                }

                override fun navigateToMoneyAccountsList(
                    navController: NavController,
                    vararg sharedElementView: View
                ) {
                    AuthorizedGraphDirections.navigateFromDashboardToMoneyAccountsList().run {
                        navController.navigate(
                            this,
                            buildExtrasForSharedElements(sharedElementView)
                        )
                    }
                }

                override fun navigateToMoneyAccountDetails(
                    navController: NavController,
                    moneyAccountId: Id,
                    transitionName: String,
                    vararg sharedElementView: View
                ) {
                    navController.navigate(
                        R.id.navigateFromDashboardToMoneyAccountDetails,
                        MoneyAccountDetailsScreenArguments(moneyAccountId, transitionName)
                            .toBundle<MoneyAccountDetailsFragment>(),
                        null,
                        buildExtrasForSharedElements(sharedElementView)
                    )
                }

            }
        }

    private fun buildExtrasForSharedElements(
        sharedElementView: Array<out View>
    ): FragmentNavigator.Extras {
        return FragmentNavigator.Extras.Builder().run {
            sharedElementView.forEach { view ->
                addSharedElement(view, view.transitionName)
            }
            build()
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

    @get:Provides
    override val moneyAccountsListNavigation: MoneyAccountsListNavigation
        get() {
            return object : MoneyAccountsListNavigation {

                override fun navigateToMoneyAccountCreation(
                    navController: NavController,
                    vararg sharedElementView: FloatingActionButton
                ) {
                    MoneyAccountsListFragmentDirections
                        .navigateFromMoneyAccountsListToMoneyAccountCreation().run {
                            navController.navigate(
                                this,
                                buildExtrasForSharedElements(sharedElementView)
                            )
                        }
                }

                override fun navigateToMoneyAccountDetails(
                    navController: NavController,
                    moneyAccountId: Id,
                    transitionName: String,
                    vararg sharedElementViews: View
                ) {
                    navController.navigate(
                        R.id.navigateFromMoneyAccountsListToMoneyAccountDetails,
                        MoneyAccountDetailsScreenArguments(moneyAccountId, transitionName)
                            .toBundle<MoneyAccountDetailsFragment>(),
                        null,
                        buildExtrasForSharedElements(sharedElementViews)
                    )
                }

            }
        }

}