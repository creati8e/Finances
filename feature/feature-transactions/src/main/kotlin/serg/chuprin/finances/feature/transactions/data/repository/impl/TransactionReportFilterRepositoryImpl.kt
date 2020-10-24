package serg.chuprin.finances.feature.transactions.data.repository.impl

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReportFilter
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.05.2020.
 */
@OptIn(FlowPreview::class)
class TransactionReportFilterRepositoryImpl @Inject constructor(
) : TransactionReportFilterRepository {

    private val filterChannel = ConflatedBroadcastChannel<TransactionsReportFilter>()

    override val filterFlow: Flow<TransactionsReportFilter>
        get() = filterChannel.asFlow()

    override fun updateFilter(newFilter: TransactionsReportFilter) {
        filterChannel.offer(newFilter)
    }

}