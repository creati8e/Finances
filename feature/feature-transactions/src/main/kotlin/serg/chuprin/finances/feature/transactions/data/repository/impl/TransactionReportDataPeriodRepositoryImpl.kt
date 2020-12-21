package serg.chuprin.finances.feature.transactions.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportDataPeriodRepository
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
class TransactionReportDataPeriodRepositoryImpl @Inject constructor(
    initialDataPeriod: DataPeriod?
) : TransactionReportDataPeriodRepository {

    override val currentDataPeriodFlow: Flow<Optional<DataPeriod>>
        get() = dataPeriodStateFlow

    private val dataPeriodStateFlow = MutableStateFlow(Optional.ofNullable(initialDataPeriod))

    override fun updateDataPeriod(dataPeriod: DataPeriod) {
        dataPeriodStateFlow.value = Optional.of(dataPeriod)
    }

}