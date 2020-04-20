package serg.chuprin.finances.injector

import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.feature.authorization.dependencies.AuthorizationDependencies
import serg.chuprin.finances.feature.authorization.dependencies.DaggerAuthorizationDependenciesComponent
import serg.chuprin.finances.feature.dashboard.dependencies.DaggerDashboardDependenciesComponent
import serg.chuprin.finances.feature.dashboard.dependencies.DashboardDependencies
import serg.chuprin.finances.feature.main.DaggerMainDependenciesComponent
import serg.chuprin.finances.feature.main.MainDependencies
import serg.chuprin.finances.feature.onboarding.DaggerOnboardingFeatureDependenciesComponent
import serg.chuprin.finances.feature.onboarding.OnboardingFeatureDependencies

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
object Injector {

    fun getAuthorizationDependencies(): AuthorizationDependencies {
        val coreProvider = CoreDependenciesComponent.get()
        return DaggerAuthorizationDependenciesComponent
            .builder()
            .coreGatewaysProvider(coreProvider)
            .coreNavigationProvider(coreProvider)
            .coreRepositoriesProvider(coreProvider)
            .build()
    }

    fun getDashboardDependencies(): DashboardDependencies {
        val coreProvider = CoreDependenciesComponent.get()
        return DaggerDashboardDependenciesComponent
            .builder()
            .coreManagerProvider(coreProvider)
            .coreServicesProvider(coreProvider)
            .coreFormattersProvider(coreProvider)
            .coreRepositoriesProvider(coreProvider)
            .build()
    }

    fun getMainDependencies(): MainDependencies {
        val coreProvider = CoreDependenciesComponent.get()
        return DaggerMainDependenciesComponent
            .builder()
            .coreGatewaysProvider(coreProvider)
            .coreRepositoriesProvider(coreProvider)
            .build()
    }

    fun getOnboardingFeatureDependencies(): OnboardingFeatureDependencies {
        val coreProvider = CoreDependenciesComponent.get()
        return DaggerOnboardingFeatureDependenciesComponent
            .builder()
            .coreUtilsProvider(coreProvider)
            .coreManagerProvider(coreProvider)
            .coreUseCasesProvider(coreProvider)
            .coreNavigationProvider(coreProvider)
            .coreFormattersProvider(coreProvider)
            .coreRepositoriesProvider(coreProvider)
            .build()
    }

}