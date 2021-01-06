package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceOnboardingEvent {

    object CloseApp : CurrencyChoiceOnboardingEvent()

    object NavigateToAccountsSetup : CurrencyChoiceOnboardingEvent()

}