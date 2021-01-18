package serg.chuprin.finances.app.di.feature

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import serg.chuprin.finances.app.di.feature.dependencies.*
import serg.chuprin.finances.app.di.navigation.AppNavigationProvider
import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.di.dependencies.HasFeatureDependencies
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.currency.choice.impl.di.CurrencyChoiceComponent
import serg.chuprin.finances.core.currency.choice.impl.di.CurrencyChoiceDependencies
import serg.chuprin.finances.feature.authorization.di.AuthorizationDependencies
import serg.chuprin.finances.feature.categories.list.di.CategoriesListDependencies
import serg.chuprin.finances.feature.dashboard.di.DashboardDependencies
import serg.chuprin.finances.feature.dashboard.setup.impl.di.DashboardWidgetsSetupComponent
import serg.chuprin.finances.feature.dashboard.setup.impl.di.DashboardWidgetsSetupDependencies
import serg.chuprin.finances.feature.moneyaccount.details.di.MoneyAccountDetailsDependencies
import serg.chuprin.finances.feature.moneyaccount.di.MoneyAccountDependencies
import serg.chuprin.finances.feature.moneyaccounts.di.MoneyAccountsListDependencies
import serg.chuprin.finances.feature.onboarding.di.OnboardingFeatureDependencies
import serg.chuprin.finances.feature.transaction.di.TransactionDependencies
import serg.chuprin.finances.feature.transactions.di.TransactionsReportDependencies
import serg.chuprin.finances.feature.userprofile.di.UserProfileDependencies

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
@AppScope
@Component(
    modules = [FeatureDependenciesModule::class],
    dependencies = [CoreDependenciesProvider::class, AppNavigationProvider::class]
)
interface FeatureDependenciesComponent : HasFeatureDependencies {

    companion object {

        private lateinit var component: FeatureDependenciesComponent

        fun initAndGet(
            coreDependencies: CoreDependenciesProvider,
            navigationProvider: AppNavigationProvider
        ): FeatureDependenciesComponent {
            component = DaggerFeatureDependenciesComponent
                .builder()
                .coreDependenciesProvider(coreDependencies)
                .appNavigationProvider(navigationProvider)
                .build()
            return component
        }

    }

}

@Module
object FeatureDependenciesModule {

    @[Provides IntoMap FeatureDependenciesKey(AuthorizationDependencies::class)]
    fun provideAuthorizationDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider
    ): FeatureDependencies {
        return DaggerAuthorizationDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(CategoriesListDependencies::class)]
    fun provideCategoriesListDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider
    ): FeatureDependencies {
        return DaggerCategoriesListDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(DashboardDependencies::class)]
    fun provideDashboardDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider,
        widgetsSetupDependencies: DashboardWidgetsSetupDependencies
    ): FeatureDependencies {
        return DaggerDashboardDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .dashboardWidgetsSetupApi(
                DashboardWidgetsSetupComponent.get(widgetsSetupDependencies)
            )
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(MoneyAccountDependencies::class)]
    fun provideMoneyAccountDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider,
        currencyChoiceDependencies: CurrencyChoiceDependencies
    ): FeatureDependencies {
        return DaggerMoneyAccountDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .currencyChoiceStoreApi(CurrencyChoiceComponent.get(currencyChoiceDependencies))
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(MoneyAccountDetailsDependencies::class)]
    fun provideMoneyAccountDetailsDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider
    ): FeatureDependencies {
        return DaggerMoneyAccountDetailsDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(MoneyAccountsListDependencies::class)]
    fun provideMoneyAccountsListDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider
    ): FeatureDependencies {
        return DaggerMoneyAccountsListDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(TransactionDependencies::class)]
    fun provideTransactionDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider
    ): FeatureDependencies {
        return DaggerTransactionDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(UserProfileDependencies::class)]
    fun provideUserProfileDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider
    ): FeatureDependencies {
        return DaggerUserProfileDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(OnboardingFeatureDependencies::class)]
    fun provideOnboardingFeatureDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider,
        currencyChoiceDependencies: CurrencyChoiceDependencies
    ): FeatureDependencies {
        return DaggerOnboardingFeatureDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .currencyChoiceStoreApi(CurrencyChoiceComponent.get(currencyChoiceDependencies))
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(TransactionsReportDependencies::class)]
    fun provideTransactionsReportDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider
    ): FeatureDependencies {
        return DaggerTransactionsReportDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(DashboardWidgetsSetupDependencies::class)]
    fun provideDashboardWidgetsSetupDependenciesIntoMap(
        dependencies: DashboardWidgetsSetupDependencies
    ): FeatureDependencies {
        return dependencies
    }

    /**
     * Provide [DashboardWidgetsSetupDependencies] as standalone dependency,
     * because it's used as API dependency.
     * Then provide it via [IntoMap] in [provideDashboardWidgetsSetupDependenciesIntoMap].
     */
    @[Provides]
    fun provideDashboardWidgetsSetupDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider
    ): DashboardWidgetsSetupDependencies {
        return DaggerDashboardWidgetsSetupDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .build()
    }

    @[Provides]
    fun provideCurrencyChoiceDependencies(
        coreDependencies: CoreDependenciesProvider
    ): CurrencyChoiceDependencies {
        return DaggerCurrencyChoiceDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .build()
    }

}
