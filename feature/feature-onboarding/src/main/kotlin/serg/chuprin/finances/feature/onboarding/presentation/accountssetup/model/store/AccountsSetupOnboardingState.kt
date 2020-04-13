package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.api.domain.model.MoneyAccountBalance
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
data class AccountsSetupOnboardingState(
    val cashBalance: MoneyAccountBalance? = null,
    val bankCardBalance: MoneyAccountBalance? = null,
    val stepState: AccountsSetupOnboardingStepState = AccountsSetupOnboardingStepState.CashQuestion
)