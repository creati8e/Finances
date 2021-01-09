package serg.chuprin.finances.core.api.domain.model.category

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
data class CategoryWithParent(
    val category: Category,
    val parentCategory: Category?
) {

    val isParent: Boolean
        get() = parentCategory == null

}