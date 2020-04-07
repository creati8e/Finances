package serg.chuprin.finances.feature.onboarding.presentation.model.store

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class OnboardingIntent {

    sealed class CurrencyChoice : OnboardingIntent() {

        object ClickOnDoneButton : CurrencyChoice()

        object ClickOnCurrencyPicker : CurrencyChoice()

    }

}