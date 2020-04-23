package serg.chuprin.finances.feature.dashboard.data.repository

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardDataPeriodRepositoryImpl @Inject constructor() : DashboardDataPeriodRepository {

    override val defaultDataPeriod: DataPeriod
        get() = _defaultDataPeriod

    private val periodChannel = ConflatedBroadcastChannel<DataPeriod>()

    private lateinit var _defaultDataPeriod: DataPeriod

    @OptIn(FlowPreview::class)
    override val currentDataPeriodFlow: Flow<DataPeriod>
        get() = periodChannel.asFlow()

    override fun setCurrentDataPeriod(dataPeriod: DataPeriod) {
        if (!::_defaultDataPeriod.isInitialized) {
            _defaultDataPeriod = dataPeriod
        }
        periodChannel.sendBlocking(dataPeriod)
    }

}