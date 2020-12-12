package serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
data class DashboardCategoryChipCell(
    val colorInt: Int,
    val chipText: String,
    val transitionName: String,
    val isOtherCategory: Boolean,
    val category: TransactionCategory?,
    val plainTransactionType: PlainTransactionType
) : DiffCell<Id> {

    override val diffCellId: Id = category?.id ?: Id.UNKNOWN

}