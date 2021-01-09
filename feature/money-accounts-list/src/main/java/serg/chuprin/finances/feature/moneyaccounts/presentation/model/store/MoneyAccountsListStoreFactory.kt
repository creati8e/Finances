package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
@ScreenScope
class MoneyAccountsListStoreFactory @Inject constructor(
    actionExecutor: MoneyAccountsListActionExecutor,
    bootstrapper: MoneyAccountsListStoreBootstrapper
) : AbsStoreFactory<MoneyAccountsListIntent, MoneyAccountsListEffect, MoneyAccountsListAction, MoneyAccountsListState, MoneyAccountsListEvent, MoneyAccountsListStore>(
    MoneyAccountsListState(),
    MoneyAccountsListStateReducer(),
    bootstrapper,
    actionExecutor,
    MoneyAccountsListAction::ExecuteIntent
)