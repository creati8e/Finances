package serg.chuprin.finances.feature.dashboard.setup.impl.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.dashboard.setup.DashboardWidgetsSetupApi
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.viewmodel.DashboardWidgetsSetupViewModel

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
@Component(
    modules = [DashboardWidgetsSetupModule::class],
    dependencies = [DashboardWidgetsSetupDependencies::class]
)
@ScreenScope
interface DashboardWidgetsSetupComponent :
    DashboardWidgetsSetupApi,
    ViewModelComponent<DashboardWidgetsSetupViewModel> {

    companion object {

        fun get(dependencies: DashboardWidgetsSetupDependencies): DashboardWidgetsSetupComponent {
            return DaggerDashboardWidgetsSetupComponent
                .builder()
                .dashboardWidgetsSetupDependencies(dependencies)
                .build()
        }

    }

}