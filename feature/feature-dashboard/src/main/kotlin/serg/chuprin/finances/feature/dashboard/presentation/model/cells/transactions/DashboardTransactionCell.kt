package serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
data class DashboardTransactionCell(
    val amount: String,
    val isIncome: Boolean,
    val formattedDate: String,
    val subcategoryName: String,
    val parentCategoryName: String,
    val transaction: Transaction
) : DiffCell<Id> {

    override val diffCellId: Id = transaction.id

}