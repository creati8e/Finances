package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
interface CurrencyChoiceStore : StateStore<CurrencyChoiceIntent, CurrencyChoiceState, Nothing>,
    CurrencyChoiceStoreIntentDispatcher