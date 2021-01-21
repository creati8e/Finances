package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreIntentDispatcher
import serg.chuprin.finances.core.test.presentation.mvi.store.TestStore

/**
 * Created by Sergey Chuprin on 19.01.2021.
 */
interface CurrencyChoiceTestStore :
    CurrencyChoiceStoreIntentDispatcher,
    TestStore<CurrencyChoiceIntent, CurrencyChoiceState, Nothing>