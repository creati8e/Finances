package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
class MoneyAccountsListStore @Inject constructor(
    executor: MoneyAccountsListActionExecutor,
    bootstrapper: MoneyAccountsListStoreBootstrapper
) : BaseStateStore<MoneyAccountsListIntent, MoneyAccountsListEffect, MoneyAccountsListAction, MoneyAccountsListState, MoneyAccountsListEvent>(
    MoneyAccountsListState(),
    MoneyAccountsListStateReducer(),
    bootstrapper,
    executor,
    MoneyAccountsListIntentToActionMapper()
)