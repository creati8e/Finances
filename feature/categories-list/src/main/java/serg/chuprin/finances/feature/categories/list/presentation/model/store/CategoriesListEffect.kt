package serg.chuprin.finances.feature.categories.list.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
sealed class CategoriesListEffect {

    class CellsBuilt(
        val cells: List<BaseCell>
    ) : CategoriesListEffect()

    class EnteredInSearchMode(
        /**
         * Cells wat previously displayed for all categories.
         */
        val allCells: List<BaseCell>,
        val searchCells: List<BaseCell>
    ) : CategoriesListEffect()

    class ExitFromSearchMode(
        /**
         * Cells wat previously displayed for all categories.
         */
        val allCells: List<BaseCell>
    ) : CategoriesListEffect()

}