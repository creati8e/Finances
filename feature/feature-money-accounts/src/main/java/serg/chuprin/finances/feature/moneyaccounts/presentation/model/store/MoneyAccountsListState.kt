package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
data class MoneyAccountsListState(
    val cells: List<BaseCell> = emptyList(),
    val accountCreationButtonIsVisible: Boolean = false
)