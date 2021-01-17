package serg.chuprin.finances.feature.dashboard.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.dashboard.presentation.model.viewmodel.DashboardViewModel
import serg.chuprin.finances.feature.dashboard.presentation.view.DashboardFragment

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@ScreenScope
@Component(
    modules = [DashboardModule::class],
    dependencies = [DashboardDependencies::class]
)
interface DashboardComponent :
    ViewModelComponent<DashboardViewModel>,
    InjectableComponent<DashboardFragment> {

    companion object {

        fun get(dependencies: DashboardDependencies): DashboardComponent {
            return DaggerDashboardComponent
                .builder()
                .dashboardDependencies(dependencies)
                .build()
        }

    }

}