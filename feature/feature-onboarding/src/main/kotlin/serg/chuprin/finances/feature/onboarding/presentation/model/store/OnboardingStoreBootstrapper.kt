package serg.chuprin.finances.feature.onboarding.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.presentation.model.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class OnboardingStoreBootstrapper @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : StoreBootstrapper<OnboardingAction> {

    override fun invoke(): Flow<OnboardingAction> {
        val onboardingStepState = when (onboardingRepository.onboardingStep) {
            OnboardingStep.COMPLETED -> {
                throw IllegalStateException(
                    "Attempt to bootstrap onboarding store is failed because onboarding is completed"
                )
            }
            OnboardingStep.ACCOUNT_SETUP -> OnboardingStepState.AccountsSetup
            OnboardingStep.CURRENCY_CHOICE -> OnboardingStepState.CurrencyChoiceState()
        }
        return flowOf(OnboardingAction.SetInitialStepState(onboardingStepState))
    }

}