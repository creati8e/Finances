package serg.chuprin.finances.core.impl.domain.linker

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.category.CategoryIdToCategory
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.05.2020.
 *
 * This class creates map of category's [Id] associated with
 * [CategoryWithParent] which contains this category and its parent if any.
 */
internal class CategoryLinker @Inject constructor() {

    fun linkWithParents(categories: List<Category>): CategoryIdToCategory {
        val map = categories.associateBy(Category::id) { category ->
            val parentCategory = if (category.parentCategoryId?.value.isNullOrEmpty()) {
                null
            } else {
                categories.find {
                    category.parentCategoryId == it.id
                }
            }
            CategoryWithParent(
                category = category,
                parentCategory = parentCategory
            )
        }
        return CategoryIdToCategory(map)
    }

}