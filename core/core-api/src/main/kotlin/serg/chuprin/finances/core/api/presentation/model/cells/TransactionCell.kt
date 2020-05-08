package serg.chuprin.finances.core.api.presentation.model.cells

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
data class TransactionCell(
    val amount: String,
    val time: String,
    val isIncome: Boolean,
    val subcategoryName: String,
    val parentCategoryName: String,
    val transaction: Transaction
) : DiffCell<Id> {

    override val diffCellId: Id = transaction.id

}