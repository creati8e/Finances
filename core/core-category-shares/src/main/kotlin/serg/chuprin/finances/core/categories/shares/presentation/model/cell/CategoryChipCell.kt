package serg.chuprin.finances.core.categories.shares.presentation.model.cell

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 24.12.2020.
 */
interface CategoryChipCell : DiffCell<Id> {

    class CellChangedPayload

    val colorInt: Int

    val chipText: String

    val transitionName: String?

    val category: TransactionCategory?

    val plainTransactionType: PlainTransactionType

    override val diffCellId: Id
        get() = category?.id ?: Id.UNKNOWN

}