package serg.chuprin.finances.feature.moneyaccount.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.mapNotNull
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreIntentDispatcher
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.moneyaccount.presentation.model.store.MoneyAccountEvent
import serg.chuprin.finances.feature.moneyaccount.presentation.model.store.MoneyAccountIntent
import serg.chuprin.finances.feature.moneyaccount.presentation.model.store.MoneyAccountState
import serg.chuprin.finances.feature.moneyaccount.presentation.model.store.MoneyAccountStore
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@ScreenScope
class MoneyAccountViewModel @Inject constructor(
    private val store: MoneyAccountStore
) : BaseStoreViewModel<MoneyAccountIntent>(),
    CurrencyChoiceStoreIntentDispatcher by store {

    val currency: Currency?
        get() = store.state.chosenCurrency

    val currencyPickerIsClickableLiveData: LiveData<Boolean> =
        store.observeParticularStateAsLiveData(MoneyAccountState::currencyPickerIsClickable)

    val accountDeletionButtonVisibilityLiveData: LiveData<Boolean> =
        store.observeParticularStateAsLiveData(MoneyAccountState::accountDeletionButtonIsVisible)

    val toolbarTitleLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData(MoneyAccountState::toolbarTitle)

    val currencyCellsLiveData: LiveData<List<BaseCell>> =
        store.observeParticularStateAsLiveData(MoneyAccountState::currentCells)

    val accountNameLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData(MoneyAccountState::moneyAccountName)

    val balanceStateLiveData: LiveData<BigDecimal> = store
        .stateFlow
        .mapNotNull { state -> state.balance }
        .asLiveData()

    val savingButtonIsEnabledLiveData: LiveData<Boolean> =
        store.observeParticularStateAsLiveData(MoneyAccountState::savingButtonIsEnabled)

    val chosenCurrencyDisplayNameLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData(MoneyAccountState::chosenCurrencyDisplayName)

    val currencyPickerVisibilityLiveData =
        store.observeParticularStateAsLiveData(MoneyAccountState::currencyPickerIsVisible)

    val eventLiveData: LiveData<MoneyAccountEvent> = store.observeEventsAsLiveData()

    val savingButtonIsEnabled: Boolean
        get() = store.state.savingButtonIsEnabled

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}