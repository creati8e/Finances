package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.api.domain.model.AccountBalance
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
sealed class AccountsSetupOnboardingEffect {

    class AccountBalanceEntered(
        val cashBalance: AccountBalance?,
        val bankCardBalance: AccountBalance?
    ) : AccountsSetupOnboardingEffect()

    class StepChanged(
        val stepState: AccountsSetupOnboardingStepState
    ) : AccountsSetupOnboardingEffect()

    class AmountEntered(
        val formattedAmount: String,
        val acceptButtonIsVisible: Boolean
    ) : AccountsSetupOnboardingEffect()

}