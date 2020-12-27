package serg.chuprin.finances.feature.dashboard.setup

import serg.chuprin.finances.feature.dashboard.setup.domain.repository.DashboardWidgetsRepository

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
interface DashboardWidgetsSetupApi {

    val dashboardWidgetsRepository: DashboardWidgetsRepository

}