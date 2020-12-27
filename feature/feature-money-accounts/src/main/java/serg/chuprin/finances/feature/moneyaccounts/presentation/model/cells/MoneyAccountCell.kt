package serg.chuprin.finances.feature.moneyaccounts.presentation.model.cells

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
data class MoneyAccountCell(
    val name: String,
    val balance: String,
    val transitionName: String,
    val moneyAccount: MoneyAccount,
    val favoriteIconIsVisible: Boolean
) : DiffCell<Id> {

    override val diffCellId: Id = moneyAccount.id

}