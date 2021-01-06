package serg.chuprin.finances.core.impl.presentation.model.store.currencychoice

import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class CurrencyChoiceStateReducer :
    StoreStateReducer<CurrencyChoiceEffect, CurrencyChoiceState> {

    override fun invoke(
        effect: CurrencyChoiceEffect,
        state: CurrencyChoiceState
    ): CurrencyChoiceState {
        return when (effect) {
            is CurrencyChoiceEffect.CurrencyChosen -> {
                state.copy(
                    currencyPickerIsVisible = false,
                    chosenCurrency = effect.currency,
                    currentCells = effect.allCurrencyCellsWithChosen,
                    defaultCurrencyCells = effect.allCurrencyCellsWithChosen,
                    chosenCurrencyDisplayName = effect.chosenCurrencyDisplayName
                )
            }
            is CurrencyChoiceEffect.SetCurrencyParams -> {
                state.copy(
                    currencyPickerIsVisible = false,
                    chosenCurrency = effect.currentCurrency,
                    currentCells = effect.allCurrencyCellsWithChosen,
                    availableCurrencies = effect.availableCurrencies,
                    defaultCurrencyCells = effect.allCurrencyCellsWithChosen,
                    chosenCurrencyDisplayName = effect.chosenCurrencyDisplayName
                )
            }
            is CurrencyChoiceEffect.CurrencyPickerVisibilityChanged -> {
                state.copy(
                    currentCells = effect.currentCells,
                    currencyPickerIsVisible = effect.visible
                )
            }
            is CurrencyChoiceEffect.CurrenciesFilteredByQuery -> {
                state.copy(currentCells = effect.currentCells)
            }
        }
    }

}