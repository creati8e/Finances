package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
sealed class CategoriesListState(
    open val cells: List<BaseCell> = emptyList()
) {

    data class AllCategories(
        override val cells: List<BaseCell> = emptyList()
    ) : CategoriesListState(cells)

    data class Search(
        override val cells: List<BaseCell> = emptyList()
    ) : CategoriesListState(cells)

}