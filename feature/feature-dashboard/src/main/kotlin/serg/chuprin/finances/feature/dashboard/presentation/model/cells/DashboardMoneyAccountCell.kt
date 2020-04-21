package serg.chuprin.finances.feature.dashboard.presentation.model.cells

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
data class DashboardMoneyAccountCell(
    val name: String,
    val balance: String,
    val moneyAccount: MoneyAccount
) : DiffCell<Id> {

    override val diffCellId: Id = moneyAccount.id

}