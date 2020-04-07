package serg.chuprin.finances.feature.onboarding.presentation.model.store

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class OnboardingEvent {

    sealed class CurrencyChoice : OnboardingEvent() {

        object ShowCurrencyPicker : CurrencyChoice()

    }

    sealed class Navigation : OnboardingEvent() {

        object NavigateToAccountSetupScreen : Navigation()

        object NavigateToCurrencyChoiceScreen : Navigation()

    }

}