package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsEvent
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsIntent
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsState
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsViewModel @Inject constructor(
    store: MoneyAccountDetailsStore
) : BaseStoreViewModel<MoneyAccountDetailsIntent>() {

    val balanceLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData(MoneyAccountDetailsState::moneyAccountBalance)

    val accountNameLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData(MoneyAccountDetailsState::moneyAccountName)

    val isFavoriteLiveData: LiveData<Boolean> =
        store.observeParticularStateAsLiveData(MoneyAccountDetailsState::isFavorite)

    val cellsLiveData: LiveData<List<BaseCell>> =
        store.observeParticularStateAsLiveData(MoneyAccountDetailsState::cells)

    val eventsLiveData: LiveData<MoneyAccountDetailsEvent> = store.observeEventsAsLiveData()

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}