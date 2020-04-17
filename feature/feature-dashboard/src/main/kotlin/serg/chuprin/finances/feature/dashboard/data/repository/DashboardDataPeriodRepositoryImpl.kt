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

    private val periodChannel = ConflatedBroadcastChannel<DataPeriod>()

    @OptIn(FlowPreview::class)
    override val currentDataPeriodFlow: Flow<DataPeriod>
        get() = periodChannel.asFlow()

    override fun setCurrentDataPeriod(dataPeriod: DataPeriod) {
        periodChannel.sendBlocking(dataPeriod)
    }

}