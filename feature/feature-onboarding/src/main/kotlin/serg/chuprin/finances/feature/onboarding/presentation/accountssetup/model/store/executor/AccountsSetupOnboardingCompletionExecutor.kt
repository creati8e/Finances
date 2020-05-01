package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor

import androidx.annotation.StringRes
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.domain.OnboardingMoneyAccountCreationParams
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteAccountsSetupOnboardingUseCase
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class AccountsSetupOnboardingCompletionExecutor @Inject constructor(
    private val resourceManger: ResourceManger,
    private val completeOnboardingUseCase: CompleteAccountsSetupOnboardingUseCase
) {

    suspend fun completeOnboarding(
        cashBalance: BigDecimal?,
        bankCardBalance: BigDecimal?
    ) {
        val bankCardAccountCreationParams = bankCardBalance?.let { balance ->
            val name = getString(R.string.bank_card_money_account_default_name)
            OnboardingMoneyAccountCreationParams(name, balance)
        }
        val cashAccountCreationParams = cashBalance?.let { balance ->
            val name = getString(R.string.cash_money_account_default_name)
            OnboardingMoneyAccountCreationParams(name, balance)
        }
        completeOnboardingUseCase.execute(
            cashAccountParams = cashAccountCreationParams,
            bankAccountCardParams = bankCardAccountCreationParams
        )
    }

    private fun getString(@StringRes stringRes: Int): String {
        return resourceManger.getString(stringRes)
    }

}