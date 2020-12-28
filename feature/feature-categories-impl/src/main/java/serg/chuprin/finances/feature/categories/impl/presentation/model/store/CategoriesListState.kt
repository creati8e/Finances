package serg.chuprin.finances.feature.categories.impl.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
data class CategoriesListState(
    val cells: List<BaseCell> = emptyList()
)