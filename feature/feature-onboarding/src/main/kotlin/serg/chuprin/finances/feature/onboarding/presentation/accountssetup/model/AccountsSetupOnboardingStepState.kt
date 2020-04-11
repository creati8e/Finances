package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model

import serg.chuprin.finances.core.api.extensions.EMPTY_STRING

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
    class CashAmountEnter(
        val amount: String = EMPTY_STRING
    ) : AccountsSetupOnboardingStepState()

    /**
     * 3 step.
     */
    object BankCardQuestion : AccountsSetupOnboardingStepState()

    /**
     * 4 step.
     */
    class BankCardAmountEnter(
        val amount: String = EMPTY_STRING
    ) : AccountsSetupOnboardingStepState()

    /**
     * 5 step.
     */
    object EverythingIsSetUp : AccountsSetupOnboardingStepState()

}