package serg.chuprin.finances.feature.dashboard.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
interface DashboardTransactionCategoriesTypeRepository {

    val transactionCategoriesType: Flow<PlainTransactionType>

    fun setType(type: PlainTransactionType)

}