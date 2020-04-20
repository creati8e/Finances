package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionCategory

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
interface TransactionCategoryRepository {

    suspend fun createPredefinedCategories(userId: Id)

    fun categoriesFlow(categoryIds: List<Id>): Flow<List<TransactionCategory>>

}