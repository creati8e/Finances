package serg.chuprin.finances.feature.dashboard.setup.impl.domain.usecase

import serg.chuprin.finances.feature.dashboard.setup.domain.model.CustomizableDashboardWidget
import serg.chuprin.finances.feature.dashboard.setup.domain.repository.DashboardWidgetsRepository
import serg.chuprin.finances.feature.dashboard.setup.impl.di.ScreenScoped
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
class UpdateDashboardWidgetsUseCase @Inject constructor(
    @ScreenScoped
    private val dashboardWidgetsRepository: DashboardWidgetsRepository
) {

    fun execute(widgets: Set<CustomizableDashboardWidget>) {
        dashboardWidgetsRepository.updateWidgets(widgets)
    }

}