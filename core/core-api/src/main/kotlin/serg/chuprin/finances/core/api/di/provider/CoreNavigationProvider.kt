package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.presentation.navigation.AuthorizationNavigation
import serg.chuprin.finances.core.api.presentation.navigation.DashboardNavigation
import serg.chuprin.finances.core.api.presentation.navigation.MoneyAccountsListNavigation
import serg.chuprin.finances.core.api.presentation.navigation.OnboardingNavigation

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface CoreNavigationProvider {

    val dashboardNavigation: DashboardNavigation

    val onboardingNavigation: OnboardingNavigation

    val authorizationNavigation: AuthorizationNavigation

    val moneyAccountsListNavigation: MoneyAccountsListNavigation

}