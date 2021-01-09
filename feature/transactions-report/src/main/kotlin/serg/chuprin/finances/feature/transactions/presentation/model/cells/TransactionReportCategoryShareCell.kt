package serg.chuprin.finances.feature.transactions.presentation.model.cells

import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategoryShareCell

/**
 * Created by Sergey Chuprin on 24.12.2020.
 */
data class TransactionReportCategoryShareCell(
    override val colorInt: Int,
    override val text: String,
    override val category: Category?,
    override val plainTransactionType: PlainTransactionType
) : CategoryShareCell {

    override val transitionName: String? = null

}