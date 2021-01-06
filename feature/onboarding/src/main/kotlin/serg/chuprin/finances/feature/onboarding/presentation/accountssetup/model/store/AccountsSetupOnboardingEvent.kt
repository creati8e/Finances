package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
sealed class AccountsSetupOnboardingEvent {

    object NavigateToDashboard : AccountsSetupOnboardingEvent()

}