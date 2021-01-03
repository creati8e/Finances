package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
sealed class MoneyAccountsListEffect {

    class CellsBuilt(
        val cells: List<BaseCell>
    ) : MoneyAccountsListEffect()

    class AccountCreationButtonVisibilityChanged(
        val isVisible: Boolean
    ) : MoneyAccountsListEffect()

}