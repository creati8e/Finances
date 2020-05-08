package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
sealed class MoneyAccountsListEvent {

    // TODO: Create navigator.
    class NavigateToMoneyAccountDetailsScreen(
        val moneyAccountId: Id,
        val transitionName: String
    ) : MoneyAccountsListEvent()

}