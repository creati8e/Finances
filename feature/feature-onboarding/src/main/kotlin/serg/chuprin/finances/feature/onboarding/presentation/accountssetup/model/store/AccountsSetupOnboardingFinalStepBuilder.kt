package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingStepState
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class AccountsSetupOnboardingFinalStepBuilder @Inject constructor(
    private val resourceManger: ResourceManger
) {

    fun build(
        cashBalance: BigDecimal?,
        bankCardBalance: BigDecimal?
    ): AccountsSetupOnboardingStepState {
        val message = if (bankCardBalance == null && cashBalance == null) {
            // User didn't setup any account.
            R.string.onboarding_accounts_setup_subtitle_no_accounts
        } else {
            R.string.onboarding_accounts_setup_subtitle_setup_finished
        }
        return AccountsSetupOnboardingStepState.EverythingIsSetUp(resourceManger.getString(message))
    }

}