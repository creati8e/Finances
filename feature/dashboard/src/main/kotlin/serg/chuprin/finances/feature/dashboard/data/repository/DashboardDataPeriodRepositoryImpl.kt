package serg.chuprin.finances.feature.dashboard.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardDataPeriodRepositoryImpl @Inject constructor() : DashboardDataPeriodRepository {

    override val defaultDataPeriod: DataPeriod
        get() = _defaultDataPeriod

    override val currentDataPeriodFlow: Flow<DataPeriod>
        get() = periodStateFlow.filterNotNull()

    private val periodStateFlow = MutableStateFlow<DataPeriod?>(null)

    private lateinit var _defaultDataPeriod: DataPeriod

    override fun setCurrentDataPeriod(dataPeriod: DataPeriod) {
        if (!::_defaultDataPeriod.isInitialized) {
            _defaultDataPeriod = dataPeriod
        }
        periodStateFlow.value = dataPeriod
    }

}