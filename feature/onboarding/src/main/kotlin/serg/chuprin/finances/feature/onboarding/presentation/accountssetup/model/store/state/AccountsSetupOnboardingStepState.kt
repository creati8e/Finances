package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state

import serg.chuprin.finances.core.api.presentation.model.AmountInputState

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
sealed class AccountsSetupOnboardingStepState {

    /**
     * 1 step.
     */
    object CashQuestion : AccountsSetupOnboardingStepState()

    /**
     * 2 step.
     */
    data class CashAmountEnter(
        val amountInputState: AmountInputState = AmountInputState(),
        val acceptAmountButtonIsEnabled: Boolean = false
    ) : AccountsSetupOnboardingStepState()

    /**
     * 3 step.
     */
    object BankCardQuestion : AccountsSetupOnboardingStepState()

    /**
     * 4 step.
     */
    data class BankCardAmountEnter(
        val amountInputState: AmountInputState = AmountInputState(),
        val acceptAmountButtonIsEnabled: Boolean = false
    ) : AccountsSetupOnboardingStepState()

    /**
     * 5 step.
     */
    class EverythingIsSetUp(
        val message: String
    ) : AccountsSetupOnboardingStepState()

    val amountInputStateOrNull: AmountInputState?
        get() {
            return (this as? CashAmountEnter)?.amountInputState
                ?: (this as? BankCardAmountEnter)?.amountInputState
        }

}