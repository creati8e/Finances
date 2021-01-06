package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceOnboardingIntent {

    /**
     * User pressed back. Maybe he want to close currency picker or close the app.
     */
    object BackPress : CurrencyChoiceOnboardingIntent()

    /**
     * User has chosen currency and want to accept it.
     */
    object ClickOnDoneButton : CurrencyChoiceOnboardingIntent()

}