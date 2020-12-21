package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.CategoriesQueryResult
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.impl.data.TransactionCategoryLinker
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
    private val categoryLinker: TransactionCategoryLinker,
    private val firebaseDataSource: FirebaseTransactionCategoryDataSource,
    private val predefinedCategoriesDataSource: PredefinedTransactionCategoriesDataSource
) : TransactionCategoryRepository {

    override suspend fun categories(
        query: TransactionCategoriesQuery
    ): Map<Id, TransactionCategoryWithParent> {
        return categoriesFlow(query).first()
    }

    override fun categoriesFlow(query: TransactionCategoriesQuery): Flow<CategoriesQueryResult> {
        return firebaseDataSource
            .categoriesFlow(query)
            .map { documents ->
                CategoriesQueryResult(
                    documents.mapNotNull(mapper::mapFromSnapshot).linkWithParents()
                )
            }
    }

    override suspend fun getUserCategories(
        userId: Id,
        type: TransactionCategoryType
    ): Map<Id, TransactionCategoryWithParent> {
        return firebaseDataSource
            .getAllUserCategories(userId)
            .mapNotNull { snapshot ->
                mapper
                    .mapFromSnapshot(snapshot)
                    ?.takeIf { category -> category.type == type }
            }
            .linkWithParents()
    }

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

    private fun List<TransactionCategory>.linkWithParents(): Map<Id, TransactionCategoryWithParent> {
        return categoryLinker.linkWithParents(this)
    }

}