package serg.chuprin.finances.core.currency.choice.impl.presentation.model.store

import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceState
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
                    currentCells = effect.unfilteredCells,
                    defaultCurrencyCells = effect.unfilteredCells,
                    chosenCurrencyDisplayName = effect.chosenCurrencyDisplayName
                )
            }
            is CurrencyChoiceEffect.SetCurrencyParams -> {
                state.copy(
                    currencyPickerIsVisible = false,
                    chosenCurrency = effect.chosenCurrency,
                    currentCells = effect.unfilteredCells,
                    defaultCurrencyCells = effect.unfilteredCells,
                    availableCurrencies = effect.availableCurrencies,
                    chosenCurrencyDisplayName = effect.chosenCurrencyDisplayName
                )
            }
            is CurrencyChoiceEffect.CurrencyPickerVisibilityChanged -> {
                state.copy(
                    currentCells = effect.unfilteredCells,
                    currencyPickerIsVisible = effect.visible
                )
            }
            is CurrencyChoiceEffect.CurrenciesFilteredByQuery -> {
                state.copy(currentCells = effect.filteredCells)
            }
        }
    }

}