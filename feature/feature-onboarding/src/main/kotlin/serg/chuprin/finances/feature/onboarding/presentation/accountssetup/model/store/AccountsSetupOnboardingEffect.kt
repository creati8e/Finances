package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.api.domain.model.MoneyAccountBalance
import serg.chuprin.finances.core.api.presentation.model.AmountInputState
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

    class EnteredAmountParsedWithError(
        val stepState: AccountsSetupOnboardingStepState
    ) : AccountsSetupOnboardingEffect()

    class CurrencyIsSet(
        val currency: Currency
    ) : AccountsSetupOnboardingEffect()

    class StepChanged(
        val stepState: AccountsSetupOnboardingStepState
    ) : AccountsSetupOnboardingEffect()

    class AmountEntered(
        val acceptButtonIsVisible: Boolean,
        val amountInputState: AmountInputState
    ) : AccountsSetupOnboardingEffect()

}