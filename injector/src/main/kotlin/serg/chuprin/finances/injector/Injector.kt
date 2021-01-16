package serg.chuprin.finances.injector

import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.feature.main.dependencies.AppLauncherDependencies
import serg.chuprin.finances.feature.main.dependencies.AuthorizedGraphLauncherDependencies
import serg.chuprin.finances.feature.main.dependencies.DaggerAppLauncherDependenciesComponent
import serg.chuprin.finances.feature.main.dependencies.DaggerAuthorizedGraphLauncherDependenciesComponent
import serg.chuprin.finances.feature.onboarding.dependencies.DaggerOnboardingFeatureDependenciesComponent
import serg.chuprin.finances.feature.onboarding.dependencies.OnboardingFeatureDependencies

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
object Injector {

    fun getAppLauncherDependencies(): AppLauncherDependencies {
        return DaggerAppLauncherDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getAuthorizedGraphLauncherDependencies(): AuthorizedGraphLauncherDependencies {
        return DaggerAuthorizedGraphLauncherDependenciesComponent
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

}