package serg.chuprin.finances.core.currency.choice.api.presentation.model.store

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 06.04.2020.
 *
 * Contains logic for navigating and filtering through currencies list.
 * It's "embeddable" store and used as part of other store.
 */
interface CurrencyChoiceStore : StateStore<CurrencyChoiceIntent, CurrencyChoiceState, Nothing>,
    CurrencyChoiceStoreIntentDispatcher