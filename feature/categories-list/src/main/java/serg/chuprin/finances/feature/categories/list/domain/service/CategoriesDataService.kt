package serg.chuprin.finances.feature.categories.list.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithChildren
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class CategoriesDataService @Inject constructor(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
) {

    private companion object {

        private val categoryNameComparator = compareBy<CategoryWithChildren> {
            it.category.name
        }

        private val categoryNameComparator1 = compareBy<Category> {
            it.name
        }

    }

    /**
     * @return flow of set of parent categories with their children list.
     */
    fun dataFlow(categoryType: CategoryType?): Flow<Set<CategoryWithChildren>> {
        return userRepository
            .currentUserSingleFlow()
            .flatMapLatest { user ->
                categoryRepository
                    .categoriesFlow(
                        CategoriesQuery(
                            ownerId = user.id,
                            type = categoryType,
                            categoryIds = emptySet()
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
        parentCategory: Category,
        categories: Collection<CategoryWithParent>
    ): CategoryWithChildren {
        return CategoryWithChildren(
            category = parentCategory,
            children = filterChildrenCategories(
                categories = categories,
                parentCategoryId = parentCategory.id
            )
        )
    }

    private fun filterChildrenCategories(
        parentCategoryId: Id,
        categories: Collection<CategoryWithParent>
    ): Collection<Category> {
        return categories.mapNotNullTo(sortedSetOf(categoryNameComparator1)) { category ->
            if (category.parentCategory?.id == parentCategoryId) {
                category.category
            } else {
                null
            }
        }
    }

}