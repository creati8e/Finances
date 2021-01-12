package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state

import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
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
    data class CashBalanceEnter(
        val currencySymbol: String = EMPTY_STRING,
        val balance: BigDecimal? = BigDecimal.ZERO,
        val acceptBalanceButtonIsEnabled: Boolean = false
    ) : AccountsSetupOnboardingStepState()

    /**
     * 3 step.
     */
    object BankCardQuestion : AccountsSetupOnboardingStepState()

    /**
     * 4 step.
     */
    data class BankCardBalanceEnter(
        val currencySymbol: String = EMPTY_STRING,
        val balance: BigDecimal? = BigDecimal.ZERO,
        val acceptBalanceButtonIsEnabled: Boolean = false
    ) : AccountsSetupOnboardingStepState()

    /**
     * 5 step.
     */
    class EverythingIsSetUp(
        val message: String
    ) : AccountsSetupOnboardingStepState()

    val balanceOrNull: BigDecimal?
        get() {
            return (this as? CashBalanceEnter)?.balance
                ?: (this as? BankCardBalanceEnter)?.balance
        }

    val currencySymbolOrEmpty: String
        get() {
            return (this as? CashBalanceEnter)?.currencySymbol
                ?: (this as? BankCardBalanceEnter)?.currencySymbol.orEmpty()
        }

}