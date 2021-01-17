package serg.chuprin.finances.app.di.navigation

import serg.chuprin.finances.core.api.presentation.navigation.TransactionReportNavigation
import serg.chuprin.finances.core.api.presentation.navigation.*

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface AppNavigationProvider {

    val transactionNavigation: TransactionNavigation

    val dashboardNavigation: DashboardNavigation

    val onboardingNavigation: OnboardingNavigation

    val userProfileNavigation: UserProfileNavigation

    val authorizationNavigation: AuthorizationNavigation

    val moneyAccountsListNavigation: MoneyAccountsListNavigation

    val transactionReportNavigation: TransactionReportNavigation

    val moneyAccountDetailsNavigation: MoneyAccountDetailsNavigation

}