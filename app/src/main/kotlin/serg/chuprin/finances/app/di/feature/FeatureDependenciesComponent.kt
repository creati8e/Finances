package serg.chuprin.finances.app.di.feature

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import serg.chuprin.finances.app.di.feature.dependencies.*
import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.di.dependencies.HasFeatureDependencies
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.impl.di.dependencies.FeatureDependenciesKey
import serg.chuprin.finances.feature.authorization.di.AuthorizationDependencies
import serg.chuprin.finances.feature.categories.list.di.CategoriesListDependencies
import serg.chuprin.finances.feature.dashboard.di.DashboardDependencies
import serg.chuprin.finances.feature.dashboard.setup.impl.di.DashboardWidgetsSetupComponent
import serg.chuprin.finances.feature.dashboard.setup.impl.di.DashboardWidgetsSetupDependencies
import serg.chuprin.finances.feature.moneyaccount.creation.di.MoneyAccountCreationDependencies
import serg.chuprin.finances.feature.moneyaccount.details.di.MoneyAccountDetailsDependencies
import serg.chuprin.finances.feature.moneyaccounts.di.MoneyAccountsListDependencies
import serg.chuprin.finances.feature.onboarding.di.OnboardingFeatureDependencies
import serg.chuprin.finances.feature.transaction.di.TransactionDependencies
import serg.chuprin.finances.feature.transactions.di.TransactionsReportDependencies
import serg.chuprin.finances.feature.userprofile.presentation.di.UserProfileDependencies

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
@AppScope
@Component(
    modules = [FeatureDependenciesModule::class],
    dependencies = [CoreDependenciesProvider::class]
)
interface FeatureDependenciesComponent : HasFeatureDependencies {

    companion object {

        private lateinit var component: FeatureDependenciesComponent

        fun get(): FeatureDependenciesComponent = component

        fun init(coreDependencies: CoreDependenciesProvider) {
            component = DaggerFeatureDependenciesComponent
                .builder()
                .coreDependenciesProvider(coreDependencies)
                .build()
        }

    }

}

@Module
object FeatureDependenciesModule {

    @[Provides IntoMap FeatureDependenciesKey(AuthorizationDependencies::class)]
    fun provideAuthorizationDependencies(
        coreDependencies: CoreDependenciesProvider
    ): FeatureDependencies {
        return DaggerAuthorizationDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(CategoriesListDependencies::class)]
    fun provideCategoriesListDependencies(
        coreDependencies: CoreDependenciesProvider
    ): FeatureDependencies {
        return DaggerCategoriesListDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(DashboardDependencies::class)]
    fun provideDashboardDependencies(
        coreDependencies: CoreDependenciesProvider,
        widgetsSetupDependencies: DashboardWidgetsSetupDependencies
    ): FeatureDependencies {
        return DaggerDashboardDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .dashboardWidgetsSetupApi(
                DashboardWidgetsSetupComponent.get(widgetsSetupDependencies)
            )
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(MoneyAccountCreationDependencies::class)]
    fun provideMoneyAccountCreationDependencies(
        coreDependencies: CoreDependenciesProvider
    ): FeatureDependencies {
        return DaggerMoneyAccountCreationDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(MoneyAccountDetailsDependencies::class)]
    fun provideMoneyAccountDetailsDependencies(
        coreDependencies: CoreDependenciesProvider
    ): FeatureDependencies {
        return DaggerMoneyAccountDetailsDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(MoneyAccountsListDependencies::class)]
    fun provideMoneyAccountsListDependencies(
        coreDependencies: CoreDependenciesProvider
    ): FeatureDependencies {
        return DaggerMoneyAccountsListDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(TransactionDependencies::class)]
    fun provideTransactionDependencies(
        coreDependencies: CoreDependenciesProvider
    ): FeatureDependencies {
        return DaggerTransactionDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(UserProfileDependencies::class)]
    fun provideUserProfileDependencies(
        coreDependencies: CoreDependenciesProvider
    ): FeatureDependencies {
        return DaggerUserProfileDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(OnboardingFeatureDependencies::class)]
    fun provideOnboardingFeatureDependencies(
        coreDependencies: CoreDependenciesProvider
    ): FeatureDependencies {
        return DaggerOnboardingFeatureDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(TransactionsReportDependencies::class)]
    fun provideTransactionsReportDependencies(
        coreDependencies: CoreDependenciesProvider
    ): FeatureDependencies {
        return DaggerTransactionsReportDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
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
        coreDependencies: CoreDependenciesProvider
    ): DashboardWidgetsSetupDependencies {
        return DaggerDashboardWidgetsSetupDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .build()
    }

}
