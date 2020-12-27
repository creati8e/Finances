package serg.chuprin.finances.feature.dashboard.setup.impl.domain.usecase

import serg.chuprin.finances.feature.dashboard.setup.domain.model.CustomizableDashboardWidget
import serg.chuprin.finances.feature.dashboard.setup.domain.repository.DashboardWidgetsRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
class GetActiveDashboardWidgetsUseCase @Inject constructor(
    private val dashboardWidgetsRepository: DashboardWidgetsRepository
) {

    suspend fun execute(): Set<CustomizableDashboardWidget> {
        return dashboardWidgetsRepository.getWidgets()
    }

}