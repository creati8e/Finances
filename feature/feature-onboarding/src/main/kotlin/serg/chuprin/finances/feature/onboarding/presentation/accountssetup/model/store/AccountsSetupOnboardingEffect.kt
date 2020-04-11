package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
sealed class AccountsSetupOnboardingEffect {

    class StepChanged(
        val stepState: AccountsSetupOnboardingStepState
    ) : AccountsSetupOnboardingEffect()

}