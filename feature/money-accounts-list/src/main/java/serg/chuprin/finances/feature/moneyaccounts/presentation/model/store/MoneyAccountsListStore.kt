package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
@ScreenScope
class MoneyAccountsListStore @Inject constructor(
    actionExecutor: MoneyAccountsListActionExecutor,
    bootstrapper: MoneyAccountsListStoreBootstrapper
) : BaseStateStore<MoneyAccountsListIntent, MoneyAccountsListEffect, MoneyAccountsListAction, MoneyAccountsListState, MoneyAccountsListEvent>(
    MoneyAccountsListState(),
    MoneyAccountsListStateReducer(),
    bootstrapper,
    actionExecutor,
    MoneyAccountsListIntentToActionMapper()
)