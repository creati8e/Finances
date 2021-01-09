package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.model.category.CategoryIdToCategory

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
interface CategoryRepository {

    suspend fun createPredefinedCategories(ownerId: Id)

    fun categoriesFlow(query: CategoriesQuery): Flow<CategoryIdToCategory>

    fun deleteCategories(categoryIds: Collection<Id>)

    suspend fun categories(query: CategoriesQuery): CategoryIdToCategory {
        return categoriesFlow(query).firstOrNull() ?: CategoryIdToCategory(emptyMap())
    }

}