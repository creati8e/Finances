package serg.chuprin.finances.core.api.domain.model.category

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
data class CategoryWithChildren(
    val category: Category,
    val children: Collection<Category>
)