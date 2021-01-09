package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
typealias MoneyAccountDetailsStore = StateStore<MoneyAccountDetailsIntent, MoneyAccountDetailsState, MoneyAccountDetailsEvent>