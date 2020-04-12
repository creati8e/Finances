package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.mapNotNull
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingViewModel @Inject constructor(
    store: AccountsSetupOnboardingStore
) : BaseStoreViewModel<AccountsSetupOnboardingIntent>() {

    val stepStateLiveData: LiveData<AccountsSetupOnboardingStepState> =
        store.observeParticularStateAsLiveData(AccountsSetupOnboardingState::stepState)

    /**
     * Do not do any diffing in order to properly display formatted amount and replace existing input.
     */
    val formattedAmountLiveData: LiveData<String> =
        store
            .stateFlow()
            .mapNotNull { it.stepState.enteredAmountOrNull }
            .asLiveData()

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}