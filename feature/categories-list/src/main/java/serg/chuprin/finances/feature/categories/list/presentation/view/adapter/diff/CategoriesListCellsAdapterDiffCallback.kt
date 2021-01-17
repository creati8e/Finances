package serg.chuprin.finances.feature.categories.list.presentation.view.adapter.diff

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.SmartDiffCallback
import serg.chuprin.finances.feature.categories.list.presentation.model.cell.ParentCategoryCell

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
class CategoriesListCellsAdapterDiffCallback : SmartDiffCallback<BaseCell>() {

    init {
        addPayloadProvider<ParentCategoryCell> { oldCell, newCell ->
            if (oldCell.isExpanded != newCell.isExpanded) {
                ParentCategoryCell.ExpansionChangedPayload()
            } else {
                null
            }
        }
    }

}