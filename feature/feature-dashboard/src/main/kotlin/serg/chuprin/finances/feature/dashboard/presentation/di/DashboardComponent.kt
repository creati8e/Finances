package serg.chuprin.finances.feature.dashboard.presentation.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.dashboard.dependencies.DashboardDependencies
import serg.chuprin.finances.feature.dashboard.presentation.model.viewmodel.DashboardViewModel
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@ScreenScope
@Component(
    modules = [DashboardModule::class],
    dependencies = [DashboardDependencies::class]
)
interface DashboardComponent : ViewModelComponent<DashboardViewModel> {

    companion object {

        fun get(): DashboardComponent {
            return DaggerDashboardComponent
                .builder()
                .dashboardDependencies(Injector.getDashboardDependencies())
                .build()
        }

    }

}