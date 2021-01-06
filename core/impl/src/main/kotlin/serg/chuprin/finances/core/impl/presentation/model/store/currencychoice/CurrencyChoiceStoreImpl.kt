package serg.chuprin.finances.core.impl.presentation.model.store.currencychoice

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.core.mvi.store.BaseStateStore

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
internal class CurrencyChoiceStoreImpl(
    executor: CurrencyChoiceActionExecutor,
    bootstrapper: StoreBootstrapper<CurrencyChoiceAction>
) : BaseStateStore<CurrencyChoiceIntent, CurrencyChoiceEffect, CurrencyChoiceAction, CurrencyChoiceState, Nothing>(
    CurrencyChoiceState(),
    CurrencyChoiceStateReducer(),
    bootstrapper,
    executor,
    CurrencyChoiceAction::ExecuteIntent
), CurrencyChoiceStore