package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
@ScreenScope
class CurrencyChoiceStore @Inject constructor(
    executor: CurrencyChoiceActionExecutor,
    bootstrapper: CurrencyChoiceStoreBootstrapper
) : BaseStateStore<CurrencyChoiceIntent, CurrencyChoiceEffect, CurrencyChoiceAction, CurrencyChoiceState, Nothing>(
    CurrencyChoiceState(),
    CurrencyChoiceStateReducer(),
    bootstrapper,
    executor,
    CurrencyChoiceAction::ExecuteIntent
)