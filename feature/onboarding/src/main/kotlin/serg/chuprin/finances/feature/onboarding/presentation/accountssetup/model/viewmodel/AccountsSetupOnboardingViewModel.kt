package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.mapNotNull
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingEvent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStore
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingStepState
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingViewModel @Inject constructor(
    private val store: AccountsSetupOnboardingStore
) : BaseStoreViewModel<AccountsSetupOnboardingIntent>() {

    val currency: Currency
        get() = store.state.currency

    val stepStateLiveData: LiveData<AccountsSetupOnboardingStepState> =
        store.observeParticularStateAsLiveData(AccountsSetupOnboardingState::stepState)

    val eventsLiveData: LiveData<AccountsSetupOnboardingEvent> = store.observeEventsAsLiveData()

    /**
     * Do not do any diffing in order to properly display formatted balance and replace existing input.
     */
    val balanceLiveData: LiveData<BigDecimal> = store
        .stateFlow
        .mapNotNull { state -> state.stepState.balanceOrNull }
        .asLiveData()

    val currencySymbolLiveDate: LiveData<String> = store.observeParticularStateAsLiveData { state ->
        state.stepState.currencySymbolOrEmpty
    }

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}