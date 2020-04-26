package serg.chuprin.finances.feature.dashboard.data.repository

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardTransactionCategoriesTypeRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 24.04.2020.
 */
class DashboardTransactionCategoriesTypeRepositoryImpl @Inject constructor() :
    DashboardTransactionCategoriesTypeRepository {

    @OptIn(FlowPreview::class)
    override val transactionCategoriesType: Flow<PlainTransactionType>
        get() = channel.asFlow()

    private val channel = ConflatedBroadcastChannel<PlainTransactionType>()

    override fun setType(type: PlainTransactionType) {
        channel.offer(type)
    }

}