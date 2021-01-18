package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreIntentDispatcher
import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
interface MoneyAccountStore : StateStore<MoneyAccountIntent, MoneyAccountState, MoneyAccountEvent>,
    CurrencyChoiceStoreIntentDispatcher