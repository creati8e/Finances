package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingStepState
import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
sealed class AccountsSetupOnboardingEffect {

    class AccountBalanceAccepted(
        val cashBalance: BigDecimal?,
        val bankCardBalance: BigDecimal?
    ) : AccountsSetupOnboardingEffect()

    class CurrencyIsSet(
        val currency: Currency
    ) : AccountsSetupOnboardingEffect()

    class StepChanged(
        val stepState: AccountsSetupOnboardingStepState
    ) : AccountsSetupOnboardingEffect()

    class BalanceEntered(
        val acceptButtonIsEnabled: Boolean,
        val balance: BigDecimal?
    ) : AccountsSetupOnboardingEffect()

}