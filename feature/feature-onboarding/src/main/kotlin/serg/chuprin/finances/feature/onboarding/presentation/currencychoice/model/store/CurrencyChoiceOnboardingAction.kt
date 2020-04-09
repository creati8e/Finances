package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import java.util.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class CurrencyChoiceOnboardingAction {

    class SetCurrenciesParams(
        val currentCurrency: Currency,
        val availableCurrencies: Set<Currency>
    ) : CurrencyChoiceOnboardingAction()

    class ExecuteIntent(
        val intent: CurrencyChoiceOnboardingIntent
    ) : CurrencyChoiceOnboardingAction()

}