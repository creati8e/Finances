package serg.chuprin.finances.feature.transactions.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReportFilter
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.05.2020.
 */
class TransactionReportFilterRepositoryImpl @Inject constructor(
) : TransactionReportFilterRepository {

    override val filterFlow: Flow<TransactionsReportFilter>
        get() = filterStateFlow.filterNotNull()

    private val filterStateFlow = MutableStateFlow<TransactionsReportFilter?>(null)

    override fun updateFilter(newFilter: TransactionsReportFilter) {
        filterStateFlow.value = newFilter
    }

}