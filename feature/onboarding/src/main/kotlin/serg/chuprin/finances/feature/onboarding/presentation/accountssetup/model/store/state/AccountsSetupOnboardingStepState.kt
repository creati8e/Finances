package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state

import java.math.BigDecimal

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
        val balance: BigDecimal? = BigDecimal.ZERO,
        val acceptAmountButtonIsEnabled: Boolean = false
    ) : AccountsSetupOnboardingStepState()

    /**
     * 3 step.
     */
    object BankCardQuestion : AccountsSetupOnboardingStepState()

    /**
     * 4 step.
     */
    // TODO: Rename and create sealed class.
    data class BankCardAmountEnter(
        val balance: BigDecimal? = BigDecimal.ZERO,
        val acceptAmountButtonIsEnabled: Boolean = false
    ) : AccountsSetupOnboardingStepState()

    /**
     * 5 step.
     */
    class EverythingIsSetUp(
        val message: String
    ) : AccountsSetupOnboardingStepState()

    val balanceOrNull: BigDecimal?
        get() {
            return (this as? CashAmountEnter)?.balance
                ?: (this as? BankCardAmountEnter)?.balance
        }

}