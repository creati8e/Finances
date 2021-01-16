package serg.chuprin.finances.app.di.feature

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import serg.chuprin.finances.app.di.feature.dependencies.DaggerAuthorizationDependenciesComponent
import serg.chuprin.finances.app.di.feature.dependencies.DaggerCategoriesListDependenciesComponent
import serg.chuprin.finances.app.di.feature.dependencies.DaggerDashboardDependenciesComponent
import serg.chuprin.finances.app.di.feature.dependencies.DaggerDashboardWidgetsSetupDependenciesComponent
import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.di.dependencies.HasFeatureDependencies
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.impl.di.dependencies.FeatureDependenciesKey
import serg.chuprin.finances.feature.authorization.presentation.di.AuthorizationDependencies
import serg.chuprin.finances.feature.categories.impl.presentation.di.CategoriesListDependencies
import serg.chuprin.finances.feature.dashboard.presentation.di.DashboardDependencies
import serg.chuprin.finances.feature.dashboard.setup.impl.di.DashboardWidgetsSetupComponent
import serg.chuprin.finances.feature.dashboard.setup.impl.di.DashboardWidgetsSetupDependencies

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
