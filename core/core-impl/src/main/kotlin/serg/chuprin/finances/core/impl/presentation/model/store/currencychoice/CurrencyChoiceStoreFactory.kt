package serg.chuprin.finances.core.impl.presentation.model.store.currencychoice

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory

/**
 * Created by Sergey Chuprin on 09.12.2020.
 */
class CurrencyChoiceStoreFactory(
    bootstrapper: StoreBootstrapper<CurrencyChoiceAction>,
    executor: CurrencyChoiceActionExecutor,
) : AbsStoreFactory<CurrencyChoiceIntent, CurrencyChoiceEffect, CurrencyChoiceAction, CurrencyChoiceState, Nothing, CurrencyChoiceStore>(
    CurrencyChoiceState(),
    CurrencyChoiceStateReducer(),
    bootstrapper,
    executor,
    CurrencyChoiceAction::ExecuteIntent
)