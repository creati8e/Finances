package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.model.category.query.result.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.impl.data.CategoryLinker
import serg.chuprin.finances.core.impl.data.datasource.assets.PredefinedCategoriesDataSource
import serg.chuprin.finances.core.impl.data.datasource.assets.CategoryAssetDto
import serg.chuprin.finances.core.impl.data.datasource.firebase.FirebaseCategoryDataSource
import serg.chuprin.finances.core.impl.data.mapper.category.FirebaseCategoryMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class CategoryRepositoryImpl @Inject constructor(
    private val mapper: FirebaseCategoryMapper,
    private val categoryLinker: CategoryLinker,
    private val firebaseDataSource: FirebaseCategoryDataSource,
    private val predefinedCategoriesDataSource: PredefinedCategoriesDataSource
) : CategoryRepository {

    override fun deleteCategories(categories: List<Category>) {
        firebaseDataSource.deleteCategories(categories)
    }

    override fun categoriesFlow(query: CategoriesQuery): Flow<CategoriesQueryResult> {
        return firebaseDataSource
            .categoriesFlow(query)
            .map { documents ->
                CategoriesQueryResult(
                    documents.mapNotNull(mapper::mapFromSnapshot).linkWithParents()
                )
            }
    }

    override suspend fun createPredefinedCategories(ownerId: Id) {
        coroutineScope {
            val allCategories = predefinedCategoriesDataSource.getCategories().run {
                (expenseCategories + incomeCategories).mapNotNull { dto -> dto.map(ownerId) }
            }
            firebaseDataSource.createCategories(allCategories)
        }
    }

    private fun CategoryAssetDto.map(ownerId: Id): Category? {
        val type = if (isIncome) {
            CategoryType.INCOME
        } else {
            CategoryType.EXPENSE
        }
        return Category.create(
            id = id,
            type = type,
            name = name,
            colorHex = colorHex,
            ownerId = ownerId.value,
            parentCategoryId = parentCategoryId
        )
    }

    private fun List<Category>.linkWithParents(): Map<Id, CategoryWithParent> {
        return categoryLinker.linkWithParents(this)
    }

}