package serg.chuprin.finances.injector

import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.feature.authorization.dependencies.AuthorizationDependencies
import serg.chuprin.finances.feature.authorization.dependencies.DaggerAuthorizationDependenciesComponent
import serg.chuprin.finances.feature.dashboard.dependencies.DaggerDashboardDependenciesComponent
import serg.chuprin.finances.feature.dashboard.dependencies.DashboardDependencies
import serg.chuprin.finances.feature.main.dependencies.DaggerMainDependenciesComponent
import serg.chuprin.finances.feature.main.dependencies.MainDependencies
import serg.chuprin.finances.feature.moneyaccounts.list.dependencies.DaggerMoneyAccountsListDependenciesComponent
import serg.chuprin.finances.feature.moneyaccounts.list.dependencies.MoneyAccountsListDependencies
import serg.chuprin.finances.feature.onboarding.dependencies.DaggerOnboardingFeatureDependenciesComponent
import serg.chuprin.finances.feature.onboarding.dependencies.OnboardingFeatureDependencies

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
object Injector {

    fun getAuthorizationDependencies(): AuthorizationDependencies {
        return DaggerAuthorizationDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getDashboardDependencies(): DashboardDependencies {
        return DaggerDashboardDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getMainDependencies(): MainDependencies {
        return DaggerMainDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getOnboardingFeatureDependencies(): OnboardingFeatureDependencies {
        return DaggerOnboardingFeatureDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getMoneyAccountsListDependencies(): MoneyAccountsListDependencies {
        return DaggerMoneyAccountsListDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

}