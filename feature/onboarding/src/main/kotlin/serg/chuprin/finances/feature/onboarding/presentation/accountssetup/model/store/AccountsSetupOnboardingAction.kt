package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import java.util.*

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
sealed class AccountsSetupOnboardingAction {

    class SetCurrency(
        val currency: Currency
    ) : AccountsSetupOnboardingAction()

    class ExecuteIntent(
        val intent: AccountsSetupOnboardingIntent
    ) : AccountsSetupOnboardingAction()

}