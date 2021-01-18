package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
sealed class MoneyAccountsListEvent {

    class NavigateToMoneyAccountDetailsScreen(
        val screenArguments: MoneyAccountDetailsScreenArguments
    ) : MoneyAccountsListEvent()

    class ChooseMoneyAccountAndCloseScreen(
        val moneyAccountId: Id
    ) : MoneyAccountsListEvent()

    class NavigateToMoneyAccountCreationScreen(
        val screnArguments: MoneyAccountScreenArguments
    ) : MoneyAccountsListEvent()

}