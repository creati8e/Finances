package serg.chuprin.finances.feature.categories.impl.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithChildren
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.category.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class CategoriesDataService @Inject constructor(
    private val userRepository: UserRepository,
    private val categoryRepository: TransactionCategoryRepository
) {

    private companion object {

        private val categoryNameComparator = compareBy<TransactionCategoryWithChildren> {
            it.category.name
        }

    }

    /**
     * @return flow of set of parent categories with their children list.
     */
    fun dataFlow(): Flow<Set<TransactionCategoryWithChildren>> {
        return userRepository
            .currentUserSingleFlow()
            .flatMapLatest { user ->
                categoryRepository
                    .categoriesFlow(
                        TransactionCategoriesQuery(
                            categoryIds = emptySet(),
                            ownerId = user.id
                        )
                    )
            }
            .mapLatest { queryResult ->
                val categories = queryResult.values
                categories.mapNotNullTo(sortedSetOf(categoryNameComparator)) { categoryWithParent ->
                    if (categoryWithParent.isParent) {
                        buildParentCategory(categoryWithParent.category, categories)
                    } else {
                        null
                    }
                }
            }
    }

    private fun buildParentCategory(
        parentCategory: TransactionCategory,
        categories: Collection<TransactionCategoryWithParent>
    ): TransactionCategoryWithChildren {
        return TransactionCategoryWithChildren(
            category = parentCategory,
            children = filterChildrenCategories(
                categories = categories,
                parentCategoryId = parentCategory.id
            )
        )
    }

    private fun filterChildrenCategories(
        parentCategoryId: Id,
        categories: Collection<TransactionCategoryWithParent>
    ): List<TransactionCategory> {
        return categories.mapNotNull { category ->
            if (category.parentCategory?.id == parentCategoryId) {
                category.category
            } else {
                null
            }
        }
    }

}