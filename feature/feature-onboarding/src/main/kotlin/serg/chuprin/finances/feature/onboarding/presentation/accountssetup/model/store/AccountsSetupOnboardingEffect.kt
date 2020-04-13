package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.api.domain.model.MoneyAccountBalance
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState
import java.util.*

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
sealed class AccountsSetupOnboardingEffect {

    class AccountBalanceEntered(
        val cashBalance: MoneyAccountBalance?,
        val bankCardBalance: MoneyAccountBalance?
    ) : AccountsSetupOnboardingEffect()

    class CurrencyIsSet(
        val currency: Currency
    ) : AccountsSetupOnboardingEffect()

    class StepChanged(
        val stepState: AccountsSetupOnboardingStepState
    ) : AccountsSetupOnboardingEffect()

    class AmountEntered(
        val formattedAmount: String,
        val acceptButtonIsVisible: Boolean
    ) : AccountsSetupOnboardingEffect()

}