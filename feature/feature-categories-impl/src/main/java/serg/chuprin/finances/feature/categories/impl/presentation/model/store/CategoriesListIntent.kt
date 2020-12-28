package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import serg.chuprin.finances.feature.categories.impl.presentation.model.cell.ParentCategoryCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
sealed class CategoriesListIntent {

    class ClickOnParentCategoryExpansionToggle(
        val parentCategoryCell: ParentCategoryCell
    ) : CategoriesListIntent()

}