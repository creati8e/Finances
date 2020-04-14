package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import androidx.core.util.Consumer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.extensions.flow.takeUntil
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.api.presentation.model.mvi.invoke
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteCurrencyChoiceOnboardingUseCase
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.cells.CurrencyCell
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class CurrencyChoiceOnboardingActionExecutor @Inject constructor(
    private val searchCurrenciesUseCase: SearchCurrenciesUseCase,
    private val completeCurrencyChoiceOnboardingUseCase: CompleteCurrencyChoiceOnboardingUseCase
) : StoreActionExecutor<CurrencyChoiceOnboardingAction, CurrencyChoiceOnboardingState, CurrencyChoiceOnboardingEffect, CurrencyChoiceOnboardingEvent> {

    override fun invoke(
        action: CurrencyChoiceOnboardingAction,
        state: CurrencyChoiceOnboardingState,
        eventConsumer: Consumer<CurrencyChoiceOnboardingEvent>,
        actionsFlow: Flow<CurrencyChoiceOnboardingAction>
    ): Flow<CurrencyChoiceOnboardingEffect> {
        return when (action) {
            is CurrencyChoiceOnboardingAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    CurrencyChoiceOnboardingIntent.ClickOnDoneButton -> {
                        handleClickOnDoneButtonIntent(state, eventConsumer)
                    }
                    CurrencyChoiceOnboardingIntent.BackPress -> {
                        handleBackPressIntent(state, eventConsumer)
                    }
                    is CurrencyChoiceOnboardingIntent.ChooseCurrency -> {
                        handleChooseCurrencyIntent(intent, state)
                    }
                    is CurrencyChoiceOnboardingIntent.SearchCurrencies -> {
                        handleSearchCurrenciesIntent(intent, state, actionsFlow)
                    }
                    CurrencyChoiceOnboardingIntent.ClickOnCurrencyPicker -> {
                        handleCurrencyPickerVisibilityChangeIntent(visible = true, state = state)
                    }
                    CurrencyChoiceOnboardingIntent.CloseCurrencyPicker -> {
                        handleCurrencyPickerVisibilityChangeIntent(visible = false, state = state)
                    }
                }
            }
            is CurrencyChoiceOnboardingAction.SetCurrenciesParams -> {
                handleSetCurrenciesParamsAction(action)
            }
        }
    }

    private fun handleSearchCurrenciesIntent(
        intent: CurrencyChoiceOnboardingIntent.SearchCurrencies,
        state: CurrencyChoiceOnboardingState,
        actionsFlow: Flow<CurrencyChoiceOnboardingAction>
    ): Flow<CurrencyChoiceOnboardingEffect> {
        return flowOfSingleValue {
            delay(300)
            val currencies = searchCurrenciesUseCase.execute(intent.searchQuery)
            CurrencyChoiceOnboardingEffect.CurrenciesFilteredByQuery(
                if (currencies.isEmpty()) {
                    listOf(
                        ZeroDataCell(
                            iconRes = null,
                            fillParent = true,
                            contentMessageRes = null,
                            titleRes = R.string.onboarding_currency_choice_currencies_not_found
                        )
                    )
                } else {
                    buildCurrencyCells(currencies, state.chosenCurrency)
                }
            )
        }.takeUntil(actionsFlow.filter { action -> isSearchCurrenciesAction(action) })
    }

    private fun handleChooseCurrencyIntent(
        intent: CurrencyChoiceOnboardingIntent.ChooseCurrency,
        state: CurrencyChoiceOnboardingState
    ): Flow<CurrencyChoiceOnboardingEffect> {
        return flowOfSingleValue {
            val updatedCurrencyCells = buildCurrencyCells(
                currencies = state.availableCurrencies,
                chosenCurrency = intent.currencyCell.currency
            )
            CurrencyChoiceOnboardingEffect.CurrencyChosen(
                currency = intent.currencyCell.currency,
                allCurrencyCellsWithChosen = updatedCurrencyCells,
                chosenCurrencyDisplayName = intent.currencyCell.displayName
            )
        }
    }

    private fun handleBackPressIntent(
        state: CurrencyChoiceOnboardingState,
        eventConsumer: Consumer<CurrencyChoiceOnboardingEvent>
    ): Flow<CurrencyChoiceOnboardingEffect> {
        if (state.currencyPickerIsVisible) {
            return flowOf(
                CurrencyChoiceOnboardingEffect.CurrencyPickerVisibilityChanged(
                    visible = false,
                    allCurrencyCells = state.defaultCurrencyCells
                )
            )
        }
        return emptyFlowAction {
            eventConsumer(CurrencyChoiceOnboardingEvent.CloseApp)
        }
    }

    private fun handleCurrencyPickerVisibilityChangeIntent(
        visible: Boolean,
        state: CurrencyChoiceOnboardingState
    ): Flow<CurrencyChoiceOnboardingEffect> {
        if (visible == state.currencyPickerIsVisible) {
            return emptyFlow()
        }
        return flowOf(
            CurrencyChoiceOnboardingEffect.CurrencyPickerVisibilityChanged(
                visible = visible,
                allCurrencyCells = state.defaultCurrencyCells
            )
        )
    }

    private fun handleClickOnDoneButtonIntent(
        state: CurrencyChoiceOnboardingState,
        eventConsumer: Consumer<CurrencyChoiceOnboardingEvent>
    ): Flow<CurrencyChoiceOnboardingEffect> {
        if (state.chosenCurrency == null) {
            return emptyFlow()
        }
        return flow {
            completeCurrencyChoiceOnboardingUseCase.execute(state.chosenCurrency)
            eventConsumer(CurrencyChoiceOnboardingEvent.NavigateToAccountsSetup)
        }
    }

    private fun handleSetCurrenciesParamsAction(
        action: CurrencyChoiceOnboardingAction.SetCurrenciesParams
    ): Flow<CurrencyChoiceOnboardingEffect> {
        return flowOfSingleValue {
            val currencyCells = buildCurrencyCells(
                chosenCurrency = action.currentCurrency,
                currencies = action.availableCurrencies
            )
            CurrencyChoiceOnboardingEffect.SetCurrencyParams(
                currencyCells = currencyCells,
                currentCurrency = action.currentCurrency,
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

    private fun isSearchCurrenciesAction(action: CurrencyChoiceOnboardingAction): Boolean {
        return (action is CurrencyChoiceOnboardingAction.ExecuteIntent
                && action.intent is CurrencyChoiceOnboardingIntent.SearchCurrencies)
    }

    private fun Currency.buildDisplayName(locale: Locale = Locale.getDefault()): String {
        return "$currencyCode - ${displayName.capitalize(locale)}"
    }

}