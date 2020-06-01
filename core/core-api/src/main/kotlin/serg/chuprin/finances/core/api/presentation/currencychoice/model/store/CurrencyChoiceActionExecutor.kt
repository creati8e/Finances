package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.extensions.flow.takeUntil
import serg.chuprin.finances.core.api.presentation.currencychoice.model.cells.CurrencyCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class CurrencyChoiceActionExecutor @Inject constructor(
    private val searchCurrenciesUseCase: SearchCurrenciesUseCase
) : StoreActionExecutor<CurrencyChoiceAction, CurrencyChoiceState, CurrencyChoiceEffect, Nothing> {

    override fun invoke(
        action: CurrencyChoiceAction,
        state: CurrencyChoiceState,
        eventConsumer: Consumer<Nothing>,
        actionsFlow: Flow<CurrencyChoiceAction>
    ): Flow<CurrencyChoiceEffect> {
        return when (action) {
            is CurrencyChoiceAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    is CurrencyChoiceIntent.ChooseCurrency -> {
                        handleChooseCurrencyIntent(intent, state)
                    }
                    is CurrencyChoiceIntent.SearchCurrencies -> {
                        handleSearchCurrenciesIntent(intent, state, actionsFlow)
                    }
                    CurrencyChoiceIntent.ClickOnCurrencyPicker -> {
                        handleCurrencyPickerVisibilityChangeIntent(visible = true, state = state)
                    }
                    CurrencyChoiceIntent.CloseCurrencyPicker -> {
                        handleCurrencyPickerVisibilityChangeIntent(visible = false, state = state)
                    }
                }
            }
            is CurrencyChoiceAction.SetCurrenciesParams -> {
                handleSetCurrenciesParamsAction(action)
            }
        }
    }

    private fun handleSearchCurrenciesIntent(
        intent: CurrencyChoiceIntent.SearchCurrencies,
        state: CurrencyChoiceState,
        actionsFlow: Flow<CurrencyChoiceAction>
    ): Flow<CurrencyChoiceEffect> {
        return flowOfSingleValue {
            delay(300)
            val currencies = searchCurrenciesUseCase.execute(intent.searchQuery)
            CurrencyChoiceEffect.CurrenciesFilteredByQuery(
                if (currencies.isEmpty()) {
                    listOf(
                        ZeroDataCell(
                            iconRes = null,
                            fillParent = true,
                            contentMessageRes = null,
                            titleRes = R.string.currencies_search_zero_data_title
                        )
                    )
                } else {
                    buildCurrencyCells(currencies, state.chosenCurrency)
                }
            )
        }.takeUntil(actionsFlow.filter { action -> isSearchCurrenciesAction(action) })
    }

    private fun handleChooseCurrencyIntent(
        intent: CurrencyChoiceIntent.ChooseCurrency,
        state: CurrencyChoiceState
    ): Flow<CurrencyChoiceEffect> {
        return flowOfSingleValue {
            val updatedCurrencyCells = buildCurrencyCells(
                currencies = state.availableCurrencies,
                chosenCurrency = intent.currencyCell.currency
            )
            CurrencyChoiceEffect.CurrencyChosen(
                currency = intent.currencyCell.currency,
                allCurrencyCellsWithChosen = updatedCurrencyCells,
                chosenCurrencyDisplayName = intent.currencyCell.displayName
            )
        }
    }

    private fun handleCurrencyPickerVisibilityChangeIntent(
        visible: Boolean,
        state: CurrencyChoiceState
    ): Flow<CurrencyChoiceEffect> {
        if (visible == state.currencyPickerIsVisible) {
            return emptyFlow()
        }
        return flowOf(
            CurrencyChoiceEffect.CurrencyPickerVisibilityChanged(
                visible = visible,
                currentCells = state.defaultCurrencyCells
            )
        )
    }

    private fun handleSetCurrenciesParamsAction(
        action: CurrencyChoiceAction.SetCurrenciesParams
    ): Flow<CurrencyChoiceEffect> {
        return flowOfSingleValue {
            val currencyCells = buildCurrencyCells(
                chosenCurrency = action.currentCurrency,
                currencies = action.availableCurrencies
            )
            CurrencyChoiceEffect.SetCurrencyParams(
                currentCurrency = action.currentCurrency,
                allCurrencyCellsWithChosen = currencyCells,
                availableCurrencies = action.availableCurrencies,
                chosenCurrencyDisplayName = action.currentCurrency.buildDisplayName()
            )
        }
    }

    private fun buildCurrencyCells(
        currencies: List<Currency>,
        chosenCurrency: Currency?
    ): List<CurrencyCell> {
        val locale = Locale.getDefault()
        return currencies
            .sortedBy(Currency::getCurrencyCode)
            .map { currency ->
                CurrencyCell(
                    currency = currency,
                    isChosen = currency == chosenCurrency,
                    displayName = currency.buildDisplayName(locale)
                )
            }
    }

    private fun isSearchCurrenciesAction(action: CurrencyChoiceAction): Boolean {
        return (action is CurrencyChoiceAction.ExecuteIntent
                && action.intent is CurrencyChoiceIntent.SearchCurrencies)
    }

    private fun Currency.buildDisplayName(locale: Locale = Locale.getDefault()): String {
        return "$currencyCode - ${displayName.capitalize(locale)}"
    }

}