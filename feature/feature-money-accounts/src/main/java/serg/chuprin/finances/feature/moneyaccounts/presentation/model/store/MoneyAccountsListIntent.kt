package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.feature.moneyaccounts.presentation.model.cells.MoneyAccountCell

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
sealed class MoneyAccountsListIntent {

    class ClickOnMoneyAccount(
        val cell: MoneyAccountCell
    ) : MoneyAccountsListIntent()

    object ClickOnMoneyAccountCreationButton : MoneyAccountsListIntent()

}