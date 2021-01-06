package serg.chuprin.finances.feature.dashboard.domain.usecase

import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class RestoreDefaultDashboardDataPeriodUseCase @Inject constructor(
    private val dashboardDataPeriodRepository: DashboardDataPeriodRepository
) {

    fun execute() {
        with(dashboardDataPeriodRepository) {
            setCurrentDataPeriod(defaultDataPeriod)
        }
    }

}