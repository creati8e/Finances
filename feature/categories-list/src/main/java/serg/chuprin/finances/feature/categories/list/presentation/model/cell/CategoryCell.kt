package serg.chuprin.finances.feature.categories.list.presentation.model.cell

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
interface CategoryCell : DiffCell<Id> {

    val category: Category

    override val diffCellId: Id
        get() = category.id

}