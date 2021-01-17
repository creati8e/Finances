package serg.chuprin.finances.feature.categories.list.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
sealed class CategoriesListState {

    data class AllCategories(
        val allCells: List<BaseCell> = emptyList()
    ) : CategoriesListState() {

        override val cells: List<BaseCell> = allCells

    }

    data class Search(
        /**
         * Cells wat previously displayed for all categories.
         */
        val allCells: List<BaseCell> = emptyList(),
        val searchCells: List<BaseCell> = emptyList()
    ) : CategoriesListState() {

        override val cells: List<BaseCell> = searchCells

    }

    val isSearchModeActive: Boolean
        get() = this is Search

    abstract val cells: List<BaseCell>

}