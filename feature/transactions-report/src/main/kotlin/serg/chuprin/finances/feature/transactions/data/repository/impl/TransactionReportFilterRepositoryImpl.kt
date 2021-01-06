package serg.chuprin.finances.feature.transactions.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import serg.chuprin.finances.feature.transactions.domain.model.TransactionReportFilter
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.05.2020.
 */
class TransactionReportFilterRepositoryImpl @Inject constructor(
    initialFilter: TransactionReportFilter
) : TransactionReportFilterRepository {

    override val filterFlow: Flow<TransactionReportFilter>
        get() = filterStateFlow

    private val filterStateFlow = MutableStateFlow(initialFilter)

    override fun updateFilter(newFilter: TransactionReportFilter) {
        filterStateFlow.value = newFilter
    }

}