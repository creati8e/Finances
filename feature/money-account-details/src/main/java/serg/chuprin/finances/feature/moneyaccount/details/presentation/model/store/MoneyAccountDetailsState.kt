package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
data class MoneyAccountDetailsState(
    val isFavorite: Boolean = false,
    val cells: List<BaseCell> = emptyList(),
    val moneyAccountName: String = EMPTY_STRING,
    val moneyAccountBalance: String = EMPTY_STRING,
    val moneyAccount: MoneyAccount = MoneyAccount.EMPTY
)