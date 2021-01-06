package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccountBalances

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
sealed class MoneyAccountsListAction {

    class BuildMoneyAccountCells(
        val moneyAccountBalances: MoneyAccountBalances
    ) : MoneyAccountsListAction()

    class ChangeAccountCreationButtonVisibility(
        val isVisible: Boolean
    ) : MoneyAccountsListAction()

    class ExecuteIntent(
        val intent: MoneyAccountsListIntent
    ) : MoneyAccountsListAction()

}