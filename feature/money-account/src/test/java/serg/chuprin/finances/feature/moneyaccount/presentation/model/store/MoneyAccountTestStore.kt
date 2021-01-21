package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreIntentDispatcher
import serg.chuprin.finances.core.test.presentation.mvi.store.TestStore

/**
 * Created by Sergey Chuprin on 21.01.2021.
 */
interface MoneyAccountTestStore :
    TestStore<MoneyAccountIntent, MoneyAccountState, MoneyAccountEvent>,
    CurrencyChoiceStoreIntentDispatcher