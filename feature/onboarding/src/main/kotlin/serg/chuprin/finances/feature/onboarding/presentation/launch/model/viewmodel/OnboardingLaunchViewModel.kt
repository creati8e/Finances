package serg.chuprin.finances.feature.onboarding.presentation.launch.model.viewmodel

import androidx.lifecycle.LiveData
import serg.chuprin.finances.core.api.di.scopes.FeatureScope
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.CurrencyChoiceOnboardingIntent
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 *
 * This ViewModel is root ViewModel for the whole onboarding flow.
 * It observes current onboarding step and sends commands to navigate to other steps or screens.
 */
@FeatureScope
class OnboardingLaunchViewModel @Inject constructor(
    onboardingRepository: OnboardingRepository
) : BaseStoreViewModel<CurrencyChoiceOnboardingIntent>() {

    val currentStepLiveData: LiveData<OnboardingStep> =
        onboardingRepository.onboardingStepFlow.asLiveData()

}