package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.api.presentation.model.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class CurrencyChoiceOnboardingStateReducer :
    StoreStateReducer<CurrencyChoiceOnboardingEffect, CurrencyChoiceOnboardingState> {

    override fun invoke(
        effect: CurrencyChoiceOnboardingEffect,
        state: CurrencyChoiceOnboardingState
    ): CurrencyChoiceOnboardingState {
        return when (effect) {
            is CurrencyChoiceOnboardingEffect.CurrencyChosen -> {
                state.copy(
                    doneButtonIsEnabled = true,
                    currencyPickerIsVisible = false,
                    chosenCurrency = effect.currency,
                    currentCells = effect.updatedCurrencyCells,
                    chosenCurrencyDisplayName = effect.chosenCurrencyDisplayName
                )
            }
            is CurrencyChoiceOnboardingEffect.SetCurrencyParams -> {
                state.copy(
                    doneButtonIsEnabled = true,
                    currencyPickerIsVisible = false,
                    currentCells = effect.currencyCells,
                    chosenCurrency = effect.currentCurrency,
                    defaultCurrencyCells = effect.currencyCells,
                    availableCurrencies = effect.availableCurrencies,
                    chosenCurrencyDisplayName = effect.chosenCurrencyDisplayName
                )
            }
            is CurrencyChoiceOnboardingEffect.CurrenciesFilteredByQuery -> {
                state.copy(currentCells = effect.cells)
            }
            is CurrencyChoiceOnboardingEffect.CurrencyPickerVisibilityChanged -> {
                state.copy(
                    currentCells = effect.allCurrencyCells,
                    currencyPickerIsVisible = effect.visible
                )
            }
        }
    }

}