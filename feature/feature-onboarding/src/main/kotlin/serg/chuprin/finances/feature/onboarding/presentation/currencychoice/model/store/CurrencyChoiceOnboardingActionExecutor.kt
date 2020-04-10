package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import androidx.core.util.Consumer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.api.extensions.flowOfSingleValue
import serg.chuprin.finances.core.api.extensions.takeUntil
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.api.presentation.model.mvi.invoke
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
                        handleClickOnDoneButtonIntent(state)
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
            CurrencyChoiceOnboardingEffect.CurrenciesFilteredByQuery(
                buildCurrencyCells(
                    chosenCurrency = state.chosenCurrency,
                    currencies = searchCurrenciesUseCase.execute(intent.searchQuery)
                )
            )
        }.takeUntil(actionsFlow.filter { action ->
            action is CurrencyChoiceOnboardingAction.ExecuteIntent
                    && action.intent is CurrencyChoiceOnboardingIntent.SearchCurrencies
        })
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
                updatedCurrencyCells = updatedCurrencyCells,
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
        state: CurrencyChoiceOnboardingState
    ): Flow<CurrencyChoiceOnboardingEffect> {
        if (state.chosenCurrency == null) {
            return emptyFlow()
        }
        return emptyFlowAction {
            completeCurrencyChoiceOnboardingUseCase.execute()
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

    private fun Currency.buildDisplayName(locale: Locale = Locale.getDefault()): String {
        return "$currencyCode - ${displayName.capitalize(locale)}"
    }

}