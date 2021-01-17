package serg.chuprin.finances.app.di.navigation

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.app.presentation.navigation.impl.*
import serg.chuprin.finances.core.api.presentation.navigation.*

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