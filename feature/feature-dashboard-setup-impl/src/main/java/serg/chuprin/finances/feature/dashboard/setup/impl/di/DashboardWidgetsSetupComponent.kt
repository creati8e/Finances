package serg.chuprin.finances.feature.dashboard.setup.impl.di

import dagger.Component
import serg.chuprin.finances.feature.dashboard.dependencies.DashboardWidgetsSetupDependencies
import serg.chuprin.finances.feature.dashboard.setup.DashboardWidgetsSetupApi
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
@Component(
    modules = [DashboardWidgetsSetupModule::class],
    dependencies = [DashboardWidgetsSetupDependencies::class]
)
interface DashboardWidgetsSetupComponent : DashboardWidgetsSetupApi {

    companion object {

        private val instance: DashboardWidgetsSetupComponent by lazy {
            DaggerDashboardWidgetsSetupComponent
                .builder()
                .dashboardWidgetsSetupDependencies(Injector.getDashboardWidgetsSetupDependencies())
                .build()
        }

        fun get(): DashboardWidgetsSetupComponent = instance

    }

}