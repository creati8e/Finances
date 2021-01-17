package serg.chuprin.finances.app.di.navigation

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.app.presentation.navigation.impl.*
import serg.chuprin.finances.feature.authorization.presentation.AuthorizationNavigation
import serg.chuprin.finances.feature.dashboard.presentation.DashboardNavigation
import serg.chuprin.finances.feature.moneyaccount.details.presentation.MoneyAccountDetailsNavigation
import serg.chuprin.finances.feature.moneyaccounts.presentation.MoneyAccountsListNavigation
import serg.chuprin.finances.feature.onboarding.presentation.OnboardingNavigation
import serg.chuprin.finances.feature.transaction.presentation.TransactionNavigation
import serg.chuprin.finances.feature.transactions.presentation.TransactionReportNavigation
import serg.chuprin.finances.feature.userprofile.presentation.UserProfileNavigation

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@Module
object AppNavigationModule : AppNavigationProvider {

    @get:Provides
    override val moneyAccountDetailsNavigation: MoneyAccountDetailsNavigation
        get() = MoneyAccountDetailsNavigationImpl()

    @get:Provides
    override val transactionReportNavigation: TransactionReportNavigation
        get() = TransactionReportNavigationImpl()

    @get:Provides
    override val transactionNavigation: TransactionNavigation
        get() = TransactionNavigationImpl()

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
        get() = AuthorizationNavigationImpl()

}