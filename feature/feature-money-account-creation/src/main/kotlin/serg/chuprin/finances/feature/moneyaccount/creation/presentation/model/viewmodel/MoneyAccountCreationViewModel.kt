package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreIntentDispatcher
import serg.chuprin.finances.core.api.presentation.model.AmountInputState
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationEvent
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationIntent
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationState
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@ScreenScope
class MoneyAccountCreationViewModel @Inject constructor(
    private val store: MoneyAccountCreationStore
) : BaseStoreViewModel<MoneyAccountCreationIntent>(),
    CurrencyChoiceStoreIntentDispatcher by store {

    val currencyCellsLiveData: LiveData<List<BaseCell>> =
        store.observeParticularStateAsLiveData(MoneyAccountCreationState::currentCells)

    val balanceStateLiveData: LiveData<AmountInputState> =
        store.observeParticularStateAsLiveData(MoneyAccountCreationState::balanceInputState)

    val savingButtonIsEnabledLiveData: LiveData<Boolean> =
        store.observeParticularStateAsLiveData(MoneyAccountCreationState::savingButtonIsEnabled)

    val chosenCurrencyDisplayNameLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData(MoneyAccountCreationState::chosenCurrencyDisplayName)

    val currencyPickerVisibilityLiveData =
        store.observeParticularStateAsLiveData(MoneyAccountCreationState::currencyPickerIsVisible)

    val eventLiveData: LiveData<MoneyAccountCreationEvent> = store.observeEventsAsLiveData()

    val savingButtonIsEnabled: Boolean
        get() = store.state.savingButtonIsEnabled

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}