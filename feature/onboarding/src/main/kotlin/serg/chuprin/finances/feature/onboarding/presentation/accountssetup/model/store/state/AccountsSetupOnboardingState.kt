package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state

import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
data class AccountsSetupOnboardingState(
    val cashBalance: BigDecimal? = null,
    val bankCardBalance: BigDecimal? = null,
    /**
     * Represents default currency retrieved from previous onboarding step.
     * It's used for balance formatting when user enter balance for new money accounts.
     */
    val currency: Currency = Currency.getInstance(Locale.getDefault()),
    val stepState: AccountsSetupOnboardingStepState = AccountsSetupOnboardingStepState.CashQuestion
)