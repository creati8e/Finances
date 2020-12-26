package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
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

    suspend fun categories(
        query: TransactionCategoriesQuery
    ): Map<Id, TransactionCategoryWithParent>

    fun categoriesFlow(query: TransactionCategoriesQuery): Flow<CategoriesQueryResult>

    suspend fun deleteCategories(categories: List<TransactionCategory>)

}