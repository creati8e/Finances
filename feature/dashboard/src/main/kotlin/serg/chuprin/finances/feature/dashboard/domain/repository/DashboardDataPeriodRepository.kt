package serg.chuprin.finances.feature.dashboard.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
interface DashboardDataPeriodRepository {

    val defaultDataPeriod: DataPeriod

    val currentDataPeriodFlow: Flow<DataPeriod>

    fun setCurrentDataPeriod(dataPeriod: DataPeriod)

}