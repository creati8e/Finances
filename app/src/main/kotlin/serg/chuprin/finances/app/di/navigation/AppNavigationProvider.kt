package serg.chuprin.finances.app.di.navigation

import serg.chuprin.finances.feature.transactions.presentation.TransactionReportNavigation
import serg.chuprin.finances.feature.authorization.presentation.AuthorizationNavigation
import serg.chuprin.finances.feature.dashboard.presentation.DashboardNavigation
import serg.chuprin.finances.feature.moneyaccount.details.presentation.MoneyAccountDetailsNavigation
import serg.chuprin.finances.feature.moneyaccounts.presentation.MoneyAccountsListNavigation
import serg.chuprin.finances.feature.onboarding.presentation.OnboardingNavigation
import serg.chuprin.finances.feature.transaction.presentation.TransactionNavigation
import serg.chuprin.finances.feature.userprofile.presentation.UserProfileNavigation

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