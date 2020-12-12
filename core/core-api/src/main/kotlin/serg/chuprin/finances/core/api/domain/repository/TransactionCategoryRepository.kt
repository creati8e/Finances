package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
interface TransactionCategoryRepository {

    suspend fun createPredefinedCategories(userId: Id)

    suspend fun getUserCategories(
        userId: Id,
        type: TransactionCategoryType
    ): Map<Id, TransactionCategoryWithParent>

    /**
     * [Map.Entry.key] is category's id;
     */
    fun categoriesFlow(categoryIds: List<Id>): Flow<Map<Id, TransactionCategoryWithParent>>

    fun categoriesFlow(query: TransactionCategoriesQuery): Flow<CategoriesQueryResult>

}