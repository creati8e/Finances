package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountCreationEffect {

    class CurrencyChoiceStateUpdated(
        val state: CurrencyChoiceState
    ) : MoneyAccountCreationEffect()

}