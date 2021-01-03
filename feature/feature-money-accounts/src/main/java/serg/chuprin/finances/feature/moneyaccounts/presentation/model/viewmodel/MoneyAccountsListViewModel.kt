package serg.chuprin.finances.feature.moneyaccounts.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.store.MoneyAccountsListEvent
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.store.MoneyAccountsListIntent
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.store.MoneyAccountsListState
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.store.MoneyAccountsListStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
class MoneyAccountsListViewModel @Inject constructor(
    store: MoneyAccountsListStore
) : BaseStoreViewModel<MoneyAccountsListIntent>() {

    val cellsLiveData: LiveData<List<BaseCell>> =
        store.observeParticularStateAsLiveData(MoneyAccountsListState::cells)

    val moneyAccountCreationButtonVisibilityLiveData: LiveData<Boolean> =
        store.observeParticularStateAsLiveData { it.accountCreationButtonIsVisible }

    val eventsLiveData: LiveData<MoneyAccountsListEvent> = store.observeEventsAsLiveData()

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}