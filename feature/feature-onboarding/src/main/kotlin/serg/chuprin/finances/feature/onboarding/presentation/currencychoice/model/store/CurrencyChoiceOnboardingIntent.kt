package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.cells.CurrencyCell

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceOnboardingIntent {

    object BackPress : CurrencyChoiceOnboardingIntent()

    object ClickOnDoneButton : CurrencyChoiceOnboardingIntent()

    object CloseCurrencyPicker : CurrencyChoiceOnboardingIntent()

    object ClickOnCurrencyPicker : CurrencyChoiceOnboardingIntent()

    class SearchCurrencies(
        val searchQuery: String
    ) : CurrencyChoiceOnboardingIntent()

    class ChooseCurrency(
        val currencyCell: CurrencyCell
    ) : CurrencyChoiceOnboardingIntent()

}