package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
sealed class MoneyAccountDetailsEvent {

    object CloseScreen : MoneyAccountDetailsEvent()

    class NavigateToTransactionScreen(
        val screenArguments: TransactionScreenArguments
    ) : MoneyAccountDetailsEvent()

    class NavigateToMoneyAccountEditingScreen(
        val screenArguments: MoneyAccountScreenArguments.Editing
    ) : MoneyAccountDetailsEvent()

}