package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.api.presentation.model.mvi.mapper.StoreIntentToActionMapper
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingAction

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
class AccountsSetupOnboardingIntentToActionMapper :
    StoreIntentToActionMapper<AccountsSetupOnboardingIntent, AccountsSetupOnboardingAction> {

    override fun invoke(intent: AccountsSetupOnboardingIntent): AccountsSetupOnboardingAction {
        return AccountsSetupOnboardingAction.ExecuteIntent(intent)
    }

}