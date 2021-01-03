package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountDetailsScreenArguments

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
sealed class MoneyAccountsListEvent {

    class NavigateToMoneyAccountDetailsScreen(
        val screenArguments: MoneyAccountDetailsScreenArguments
    ) : MoneyAccountsListEvent()

    object NavigateToMoneyAccountCreationScreen : MoneyAccountsListEvent()

}