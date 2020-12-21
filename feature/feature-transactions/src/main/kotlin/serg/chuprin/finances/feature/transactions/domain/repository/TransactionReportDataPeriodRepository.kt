package serg.chuprin.finances.feature.transactions.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import java.util.*

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
interface TransactionReportDataPeriodRepository {

    val currentDataPeriodFlow: Flow<Optional<DataPeriod>>

    fun updateDataPeriod(dataPeriod: DataPeriod)

}