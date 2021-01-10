package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.model.category.CategoryIdToCategory
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.impl.domain.linker.CategoryLinker
import serg.chuprin.finances.core.impl.data.datasource.assets.PredefinedCategoriesDataSource
import serg.chuprin.finances.core.impl.data.datasource.firebase.FirebaseCategoryDataSource
import serg.chuprin.finances.core.impl.data.mapper.category.CategoryAssetMapper
import serg.chuprin.finances.core.impl.data.mapper.category.FirebaseCategoryMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class CategoryRepositoryImpl @Inject constructor(
    private val linker: CategoryLinker,
    private val assetMapper: CategoryAssetMapper,
    private val firebaseMapper: FirebaseCategoryMapper,
    private val firebaseDataSource: FirebaseCategoryDataSource,
    private val predefinedCategoriesDataSource: PredefinedCategoriesDataSource
) : CategoryRepository {

    override fun deleteCategories(categoryIds: Collection<Id>) {
        firebaseDataSource.deleteCategories(categoryIds)
    }

    override fun categoriesFlow(query: CategoriesQuery): Flow<CategoryIdToCategory> {
        return firebaseDataSource
            .categoriesFlow(query)
            .map { documents ->
                CategoryIdToCategory(
                    documents.mapNotNull(firebaseMapper::mapFromSnapshot).linkWithParents()
                )
            }
    }

    override suspend fun createPredefinedCategories(ownerId: Id) {
        coroutineScope {
            val allCategories = predefinedCategoriesDataSource.getCategories().run {
                (expenseCategories + incomeCategories)
                    .mapNotNull { dto ->
                        assetMapper.mapFromAsset(dto, ownerId)
                    }
            }
            firebaseDataSource.createOrUpdateCategories(allCategories)
        }
    }

    private fun List<Category>.linkWithParents(): CategoryIdToCategory {
        return linker.linkWithParents(this)
    }

}