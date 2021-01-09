package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
@ScreenScope
class MoneyAccountDetailsStoreFactory @Inject constructor(
    actionExecutor: MoneyAccountDetailsActionExecutor,
    bootstrapper: MoneyAccountDetailsStoreBootstrapper
) : AbsStoreFactory<MoneyAccountDetailsIntent, MoneyAccountDetailsEffect, MoneyAccountDetailsAction, MoneyAccountDetailsState, MoneyAccountDetailsEvent, MoneyAccountDetailsStore>(
    MoneyAccountDetailsState(),
    MoneyAccountDetailsStateReducer(),
    bootstrapper,
    actionExecutor,
    MoneyAccountDetailsAction::ExecuteIntent
)