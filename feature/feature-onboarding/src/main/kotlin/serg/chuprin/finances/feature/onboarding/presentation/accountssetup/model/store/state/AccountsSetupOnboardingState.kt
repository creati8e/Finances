package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state

import serg.chuprin.finances.core.api.domain.model.MoneyAccountBalance
import java.util.*

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
data class AccountsSetupOnboardingState(
    val cashBalance: MoneyAccountBalance? = null,
    val bankCardBalance: MoneyAccountBalance? = null,
    /**
     * Represents default currency retrieved from previous onboarding step.
     * It's used for amount formatting when user enter balance for new money accounts.
     */
    val currency: Currency = Currency.getInstance(Locale.getDefault()),
    val stepState: AccountsSetupOnboardingStepState = AccountsSetupOnboardingStepState.CashQuestion
)