package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.cells.CurrencyCell

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

    object CloseCurrencyPicker : CurrencyChoiceOnboardingIntent()

    /**
     * User want to open currency picker.
     */
    object ClickOnCurrencyPicker : CurrencyChoiceOnboardingIntent()

    class SearchCurrencies(
        val searchQuery: String
    ) : CurrencyChoiceOnboardingIntent()

    /**
     * User has chosen a particular currency from currencies list.
     */
    class ChooseCurrency(
        val currencyCell: CurrencyCell
    ) : CurrencyChoiceOnboardingIntent()

}