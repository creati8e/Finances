package serg.chuprin.finances.app.di.feature

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import serg.chuprin.finances.app.di.feature.dependencies.DaggerAuthorizationDependenciesComponent
import serg.chuprin.finances.app.di.feature.dependencies.DaggerCategoriesListDependenciesComponent
import serg.chuprin.finances.app.di.feature.dependencies.DaggerDashboardDependenciesComponent
import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.di.dependencies.HasFeatureDependencies
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.core.impl.di.dependencies.FeatureDependenciesKey
import serg.chuprin.finances.feature.authorization.presentation.di.AuthorizationDependencies
import serg.chuprin.finances.feature.categories.impl.presentation.di.CategoriesListDependencies
import serg.chuprin.finances.feature.dashboard.presentation.di.DashboardDependencies
import serg.chuprin.finances.feature.dashboard.setup.impl.di.DashboardWidgetsSetupComponent

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
@AppScope
@Component(modules = [FeatureDependenciesModule::class])
interface FeatureDependenciesComponent : HasFeatureDependencies {

    companion object {

        private lateinit var component: FeatureDependenciesComponent

        fun get(): FeatureDependenciesComponent = component

        fun init() {
            component = DaggerFeatureDependenciesComponent.create()
        }

    }

}

@Module
object FeatureDependenciesModule {

    @[Provides IntoMap FeatureDependenciesKey(AuthorizationDependencies::class)]
    fun provideAuthorizationDependencies(): FeatureDependencies {
        return DaggerAuthorizationDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(CategoriesListDependencies::class)]
    fun provideCategoriesListDependencies(): FeatureDependencies {
        return DaggerCategoriesListDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    @[Provides IntoMap FeatureDependenciesKey(DashboardDependencies::class)]
    fun provideDashboardDependencies(): FeatureDependencies {
        return DaggerDashboardDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .dashboardWidgetsSetupApi(DashboardWidgetsSetupComponent.get())
            .build()
    }

}
