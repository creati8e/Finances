package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.category.query.result.CategoriesQueryResult

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
interface TransactionCategoryRepository {

    suspend fun createPredefinedCategories(ownerId: Id)

    fun categoriesFlow(query: TransactionCategoriesQuery): Flow<CategoriesQueryResult>

    suspend fun deleteCategories(categories: List<TransactionCategory>)

    suspend fun categories(query: TransactionCategoriesQuery): CategoriesQueryResult {
        return categoriesFlow(query).firstOrNull() ?: CategoriesQueryResult(emptyMap())
    }

}