package serg.chuprin.finances.feature.transaction.presentation.model.store.executor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import serg.chuprin.finances.core.api.domain.model.category.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenCategory
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionEffect
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionIntent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
class TransactionChooseCategoryIntentExecutor @Inject constructor(
    private val categoryRepository: TransactionCategoryRepository
) {

    fun execute(
        intent: TransactionIntent.ChooseCategory,
        state: TransactionState
    ): Flow<TransactionEffect> {
        return flowOfSingleValue {
            val category = categoryRepository
                .categoriesFlow(
                    TransactionCategoriesQuery(
                        ownerId = state.userId,
                        categoryIds = setOf(intent.categoryId)
                    )
                )
                .first()
                .values
                .first()
                .category
            TransactionEffect.CategoryChanged(TransactionChosenCategory(category.name, category))
        }
    }

}