package serg.chuprin.finances.feature.dashboard.domain.usecase

import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.core.api.domain.model.DataPeriodType
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class ChangeDataPeriodTypeForDashboardUseCase @Inject constructor(
    private val dataPeriodRepository: DashboardDataPeriodRepository
) {

    fun execute(periodType: DataPeriodType) {
        dataPeriodRepository.setCurrentDataPeriod(DataPeriod.from(periodType))
    }

}