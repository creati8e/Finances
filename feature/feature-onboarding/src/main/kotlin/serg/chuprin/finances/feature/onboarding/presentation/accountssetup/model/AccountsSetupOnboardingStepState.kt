package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
sealed class AccountsSetupOnboardingStepState {

    private companion object {
        private const val INITIAL_ENTERED_AMOUNT = "0"
    }

    /**
     * 1 step.
     */
    object CashQuestion : AccountsSetupOnboardingStepState()

    /**
     * 2 step.
     */
    data class CashAmountEnter(
        val enteredAmount: String = INITIAL_ENTERED_AMOUNT,
        val acceptButtonIsVisible: Boolean = false
    ) : AccountsSetupOnboardingStepState()

    /**
     * 3 step.
     */
    object BankCardQuestion : AccountsSetupOnboardingStepState()

    /**
     * 4 step.
     */
    data class BankCardAmountEnter(
        val enteredAmount: String = INITIAL_ENTERED_AMOUNT,
        val acceptButtonIsVisible: Boolean = false
    ) : AccountsSetupOnboardingStepState()

    /**
     * 5 step.
     */
    object EverythingIsSetUp : AccountsSetupOnboardingStepState()

    val enteredAmountOrNull: String?
        get() {
            return (this as? CashAmountEnter)?.enteredAmount
                ?: (this as? BankCardAmountEnter)?.enteredAmount
        }

}