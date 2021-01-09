package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
typealias MoneyAccountsListStore = StateStore<MoneyAccountsListIntent, MoneyAccountsListState, MoneyAccountsListEvent>