package serg.chuprin.finances.feature.categories.list.presentation.model.cell

import serg.chuprin.finances.core.api.domain.model.category.Category

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
data class ParentCategoryCell(
    val color: Int,
    val isExpanded: Boolean,
    val isExpansionAvailable: Boolean,
    override val category: Category
) : CategoryCell {

    class ExpansionChangedPayload

}