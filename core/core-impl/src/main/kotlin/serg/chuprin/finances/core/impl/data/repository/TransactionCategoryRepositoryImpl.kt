package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.impl.data.datasource.assets.PredefinedTransactionCategoriesDataSource
import serg.chuprin.finances.core.impl.data.datasource.assets.TransactionCategoryAssetDto
import serg.chuprin.finances.core.impl.data.datasource.firebase.FirebaseTransactionCategoryDataSource
import serg.chuprin.finances.core.impl.data.mapper.category.FirebaseTransactionCategoryMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class TransactionCategoryRepositoryImpl @Inject constructor(
    private val mapper: FirebaseTransactionCategoryMapper,
    private val firebaseDataSource: FirebaseTransactionCategoryDataSource,
    private val predefinedCategoriesDataSource: PredefinedTransactionCategoriesDataSource
) : TransactionCategoryRepository {

    override suspend fun createPredefinedCategories(userId: Id) {
        coroutineScope {
            val allCategories = predefinedCategoriesDataSource.getCategories().run {
                (expenseCategories + incomeCategories).mapNotNull { dto -> dto.map(userId) }
            }
            firebaseDataSource.createCategories(allCategories)
        }
    }

    override fun categoriesFlow(categoryIds: List<Id>): Flow<Map<Id, TransactionCategoryWithParent>> {
        return firebaseDataSource
            .categoriesWithParentsFlow(categoryIds.map(Id::value))
            .map { documentSnapshots ->
                documentSnapshots
                    .mapNotNull(mapper::mapFromSnapshot)
                    .linkWithParents()
            }
    }

    private fun List<TransactionCategory>.linkWithParents(): Map<Id, TransactionCategoryWithParent> {
        return associateBy(
            { category -> category.id },
            { category ->
                val parentCategory = if (category.parentCategoryId?.value.isNullOrEmpty()) {
                    null
                } else {
                    find {
                        category.parentCategoryId == it.parentCategoryId
                    }
                }
                TransactionCategoryWithParent(
                    category = category,
                    parentCategory = parentCategory
                )
            }
        )
    }

    private fun TransactionCategoryAssetDto.map(userId: Id): TransactionCategory? {
        val type = if (isIncome) {
            TransactionCategoryType.INCOME
        } else {
            TransactionCategoryType.EXPENSE
        }
        return TransactionCategory.create(
            id = id,
            type = type,
            name = name,
            colorHex = colorHex,
            ownerId = userId.value,
            parentCategoryId = parentCategoryId
        )
    }

}