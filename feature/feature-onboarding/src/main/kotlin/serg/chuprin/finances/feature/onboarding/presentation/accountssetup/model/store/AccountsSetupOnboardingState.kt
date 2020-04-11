package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.api.domain.model.AccountBalance
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
data class AccountsSetupOnboardingState(
    val cashBalance: AccountBalance? = null,
    val bankCardBalance: AccountBalance? = null,
    val stepState: AccountsSetupOnboardingStepState = AccountsSetupOnboardingStepState.CashQuestion
)