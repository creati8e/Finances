package serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions

import androidx.annotation.ColorInt
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
data class DashboardTransactionCell(
    val amount: String,
    val categoryName: String,
    val formattedDate: String,
    @ColorInt val amountColor: Int,
    val transaction: Transaction
) : DiffCell<Id> {

    override val diffCellId: Id = transaction.id

}