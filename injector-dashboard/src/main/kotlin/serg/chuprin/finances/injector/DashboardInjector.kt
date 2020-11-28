package serg.chuprin.finances.injector

import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.feature.dashboard.setup.impl.di.DashboardWidgetsSetupComponent
import serg.chuprin.finances.injector.dashboard.dependencies.DaggerDashboardDependenciesComponent
import serg.chuprin.finances.injector.dashboard.dependencies.DashboardDependencies

/**
 * Created by Sergey Chuprin on 28.11.2020.
 *
 * Do not rename this class to Injector because it
 * conflicts with existing class with same name in runtime.
 */
object DashboardInjector {

    fun getDashboardDependencies(): DashboardDependencies {
        return DaggerDashboardDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .dashboardWidgetsSetupApi(DashboardWidgetsSetupComponent.get())
            .build()
    }

}