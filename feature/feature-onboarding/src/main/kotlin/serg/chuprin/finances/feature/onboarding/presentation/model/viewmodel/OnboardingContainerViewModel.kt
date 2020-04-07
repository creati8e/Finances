package serg.chuprin.finances.feature.onboarding.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
@ScreenScope
class OnboardingContainerViewModel @Inject constructor(
    store: OnboardingStore
) : BaseStoreViewModel<OnboardingIntent>() {

    val currentStepLiveData: LiveData<OnboardingStep> = liveData {
        store.stateFlow()
            .mapNotNull { state ->
                state.stepState?.step
            }
            .distinctUntilChanged()
            .collect { stepState ->
                emit(stepState)
            }
    }

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}