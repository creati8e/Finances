package serg.chuprin.finances.core.currency.choice.impl.presentation.model.store

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.currency.choice.impl.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.extensions.flow.takeUntil
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.currency.choice.api.presentation.model.cells.CurrencyCell
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceState
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
                    is CurrencyChoiceIntent.ClickOnCurrencyCell -> {
                        handleChooseCurrencyIntent(intent, state)
                    }
                    is CurrencyChoiceIntent.EnterCurrencySearchQuery -> {
                        handleSearchCurrenciesIntent(intent, state, actionsFlow)
                    }
                    CurrencyChoiceIntent.ClickOnCurrencyPicker -> {
                        handleCurrencyPickerVisibilityChangeIntent(visible = true, state = state)
                    }
                    CurrencyChoiceIntent.ClickOnCloseCurrencyPicker -> {
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
        intent: CurrencyChoiceIntent.EnterCurrencySearchQuery,
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
                    buildCellsWithChosenCurrency(currencies, state.chosenCurrency)
                }
            )
        }.takeUntil(actionsFlow.filter { action -> isSearchCurrenciesAction(action) })
    }

    private fun handleChooseCurrencyIntent(
        intent: CurrencyChoiceIntent.ClickOnCurrencyCell,
        state: CurrencyChoiceState
    ): Flow<CurrencyChoiceEffect> {
        return flowOfSingleValue {
            val cellsWithChosenCurrency = buildCellsWithChosenCurrency(
                currencies = state.availableCurrencies,
                chosenCurrency = intent.currencyCell.currency
            )
            CurrencyChoiceEffect.CurrencyChosen(
                currency = intent.currencyCell.currency,
                unfilteredCells = cellsWithChosenCurrency,
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
                unfilteredCells = state.defaultCurrencyCells
            )
        )
    }

    private fun handleSetCurrenciesParamsAction(
        action: CurrencyChoiceAction.SetCurrenciesParams
    ): Flow<CurrencyChoiceEffect> {
        return flowOfSingleValue {
            val cellsWithChosenCurrency = buildCellsWithChosenCurrency(
                chosenCurrency = action.chosenCurrency,
                currencies = action.availableCurrencies
            )
            CurrencyChoiceEffect.SetCurrencyParams(
                chosenCurrency = action.chosenCurrency,
                availableCurrencies = action.availableCurrencies,
                unfilteredCells = cellsWithChosenCurrency,
                chosenCurrencyDisplayName = action.chosenCurrency.buildDisplayName()
            )
        }
    }

    private fun buildCellsWithChosenCurrency(
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
                && action.intent is CurrencyChoiceIntent.EnterCurrencySearchQuery)
    }

    private fun Currency.buildDisplayName(locale: Locale = Locale.getDefault()): String {
        return "$currencyCode - ${displayName.capitalize(locale)}"
    }

}