package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreIntentDispatcher
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationIntent
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@ScreenScope
class MoneyAccountCreationViewModel @Inject constructor(
    store: MoneyAccountCreationStore
) : BaseStoreViewModel<MoneyAccountCreationIntent>(),
    CurrencyChoiceStoreIntentDispatcher by store {

    val currencyCellsLiveData: LiveData<List<BaseCell>> =
        store.observeParticularStateAsLiveData { state -> state.currentCells }

    val creationButtonIsEnabledLiveData: LiveData<Boolean> =
        store.observeParticularStateAsLiveData { state -> state.creationButtonIsEnabled }

    val chosenCurrencyDisplayNameLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData { state -> state.chosenCurrencyDisplayName }

    val currencyPickerVisibilityLiveData =
        store.observeParticularStateAsLiveData { state -> state.currencyPickerIsVisible }

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}