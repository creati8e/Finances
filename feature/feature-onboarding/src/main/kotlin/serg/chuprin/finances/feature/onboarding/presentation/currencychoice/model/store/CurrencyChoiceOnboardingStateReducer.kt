package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

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
                    currentCells = effect.allCurrencyCellsWithChosen,
                    defaultCurrencyCells = effect.allCurrencyCellsWithChosen,
                    chosenCurrencyDisplayName = effect.chosenCurrencyDisplayName
                )
            }
            is CurrencyChoiceOnboardingEffect.SetCurrencyParams -> {
                state.copy(
                    doneButtonIsEnabled = true,
                    currencyPickerIsVisible = false,
                    chosenCurrency = effect.currentCurrency,
                    currentCells = effect.allCurrencyCellsWithChosen,
                    availableCurrencies = effect.availableCurrencies,
                    defaultCurrencyCells = effect.allCurrencyCellsWithChosen,
                    chosenCurrencyDisplayName = effect.chosenCurrencyDisplayName
                )
            }
            is CurrencyChoiceOnboardingEffect.CurrencyPickerVisibilityChanged -> {
                state.copy(
                    currentCells = effect.currentCells,
                    currencyPickerIsVisible = effect.visible
                )
            }
            is CurrencyChoiceOnboardingEffect.CurrenciesFilteredByQuery -> {
                state.copy(currentCells = effect.currentCells)
            }
        }
    }

}