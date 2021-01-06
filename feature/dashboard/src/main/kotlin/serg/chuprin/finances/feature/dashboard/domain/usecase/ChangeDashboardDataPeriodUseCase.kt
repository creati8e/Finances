package serg.chuprin.finances.feature.dashboard.domain.usecase

import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardDataPeriodChangeDirection
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class ChangeDashboardDataPeriodUseCase @Inject constructor(
    private val dataPeriodRepository: DashboardDataPeriodRepository
) {

    fun execute(currentDataPeriod: DataPeriod, direction: DashboardDataPeriodChangeDirection) {
        val newDataPeriod = when (direction) {
            DashboardDataPeriodChangeDirection.NEXT -> currentDataPeriod.next()
            DashboardDataPeriodChangeDirection.PREVIOUS -> currentDataPeriod.previous()
        }
        dataPeriodRepository.setCurrentDataPeriod(newDataPeriod)
    }

}