package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.mapNotNull
import serg.chuprin.finances.core.api.presentation.model.AmountInputState
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingEvent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStore
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingStepState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingViewModel @Inject constructor(
    store: AccountsSetupOnboardingStore
) : BaseStoreViewModel<AccountsSetupOnboardingIntent>() {

    val stepStateLiveData: LiveData<AccountsSetupOnboardingStepState> =
        store.observeParticularStateAsLiveData(AccountsSetupOnboardingState::stepState)

    val eventsLiveData: LiveData<AccountsSetupOnboardingEvent> = store.observeEventsAsLiveData()

    /**
     * Do not do any diffing in order to properly display formatted amount and replace existing input.
     */
    val amountInputStateLiveData: LiveData<AmountInputState> =
        store
            .stateFlow
            .mapNotNull { it.stepState.amountInputStateOrNull }
            .asLiveData()

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}