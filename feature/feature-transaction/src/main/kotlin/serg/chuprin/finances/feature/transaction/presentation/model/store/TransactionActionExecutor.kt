package serg.chuprin.finances.feature.transaction.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.transaction.R
import serg.chuprin.finances.feature.transaction.presentation.model.*
import serg.chuprin.finances.feature.transaction.presentation.model.formatter.TransactionChosenDateFormatter
import serg.chuprin.finances.feature.transaction.presentation.model.store.executor.TransactionChooseCategoryIntentExecutor
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionActionExecutor @Inject constructor(
    private val amountParser: AmountParser,
    private val resourceManger: ResourceManger,
    private val amountFormatter: AmountFormatter,
    private val chosenDateFormatter: TransactionChosenDateFormatter,
    private val chooseCategoryIntentExecutor: TransactionChooseCategoryIntentExecutor
) : StoreActionExecutor<TransactionAction, TransactionState, TransactionEffect, TransactionEvent> {

    override fun invoke(
        action: TransactionAction,
        state: TransactionState,
        eventConsumer: Consumer<TransactionEvent>,
        actionsFlow: Flow<TransactionAction>
    ): Flow<TransactionEffect> {
        return when (action) {
            is TransactionAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    is TransactionIntent.EnterAmount -> {
                        handleEnterAmountIntent(intent, state)
                    }
                    TransactionIntent.ClickOnSaveButton -> {
                        handleClickOnSaveButtonIntent(eventConsumer)
                    }
                    TransactionIntent.ClickOnCloseButton -> {
                        handleClickOnCloseButtonIntent(state, eventConsumer)
                    }
                    TransactionIntent.ClickOnCategory -> {
                        handleClickOnCategoryIntent(state, eventConsumer)
                    }
                    is TransactionIntent.ChooseCategory -> {
                        handleChooseCategoryIntent(intent)
                    }
                    is TransactionIntent.ClickOnOperationType -> {
                        handleClickOnOperationTypeIntent(intent, state)
                    }
                }
            }
            is TransactionAction.FormatInitialState -> {
                handleFormatInitialStateAction(action)
            }
        }
    }

    private fun handleClickOnOperationTypeIntent(
        intent: TransactionIntent.ClickOnOperationType,
        state: TransactionState
    ): Flow<TransactionEffect> {
        if (state.operation == intent.operation) {
            return emptyFlow()
        }
        val effect = TransactionEffect.OperationChanged(intent.operation)
        // Remove chosen category if new operation type is not income or expense.
        if (state.chosenCategory.category != null && !intent.operation.isPlain()) {
            return flowOf(TransactionEffect.CategoryChanged(buildChosenCategory(null)), effect)
        }
        return flowOf(effect)
    }

    private fun handleChooseCategoryIntent(
        intent: TransactionIntent.ChooseCategory
    ): Flow<TransactionEffect> {
        return chooseCategoryIntentExecutor.execute(intent)
    }

    private fun handleClickOnCategoryIntent(
        state: TransactionState,
        eventConsumer: Consumer<TransactionEvent>
    ): Flow<TransactionEffect> {
        return emptyFlowAction {
            val categoryType = when (
                (state.operation as? TransactionChosenOperation.Plain)?.type
            ) {
                PlainTransactionType.EXPENSE -> TransactionCategoryType.EXPENSE
                PlainTransactionType.INCOME -> TransactionCategoryType.INCOME
                null -> null
            }
            val screenArguments = CategoriesListScreenArguments.Picker(categoryType)
            eventConsumer(TransactionEvent.NavigateToCategoryPickerScreen(screenArguments))
        }
    }

    private fun handleClickOnCloseButtonIntent(
        state: TransactionState,
        eventConsumer: Consumer<TransactionEvent>
    ): Flow<TransactionEffect> {
        // TODO: Add check for unsaved changed.
        return emptyFlowAction {
            eventConsumer(TransactionEvent.CloseScreen)
        }
    }

    private fun handleClickOnSaveButtonIntent(
        eventConsumer: Consumer<TransactionEvent>
    ): Flow<TransactionEffect> {
        TODO("Not yet implemented")
    }

    private fun handleEnterAmountIntent(
        intent: TransactionIntent.EnterAmount,
        state: TransactionState
    ): Flow<TransactionEffect> {
        // Check if money account is initialized.
        if (state.chosenMoneyAccount.account == MoneyAccount.EMPTY) {
            return emptyFlow()
        }
        return flowOfSingleValue {
            val formattedAmount = amountFormatter.formatInput(
                input = intent.amount,
                currency = state.chosenMoneyAccount.account.currency
            )
            val parsedAmount = amountParser.parse(formattedAmount)
            TransactionEffect.AmountEntered(
                isSaveButtonEnabled = parsedAmount != null && parsedAmount != BigDecimal.ZERO,
                TransactionEnteredAmount(
                    amount = parsedAmount,
                    formatted = formattedAmount,
                    hasError = parsedAmount == null
                )
            )
        }
    }

    private fun handleFormatInitialStateAction(
        action: TransactionAction.FormatInitialState
    ): Flow<TransactionEffect> {
        return flowOf(
            TransactionEffect.DateChanged(buildChosenDate(action.date)),
            TransactionEffect.CategoryChanged(buildChosenCategory(action.category)),
            TransactionEffect.MoneyAccountChanged(buildChosenMoneyAccount(action.moneyAccount))
        )
    }

    private fun buildChosenMoneyAccount(moneyAccount: MoneyAccount): TransactionChosenMoneyAccount {
        return TransactionChosenMoneyAccount(moneyAccount.name, moneyAccount)
    }

    private fun buildChosenCategory(category: TransactionCategory?): TransactionChosenCategory {
        val formattedName = category?.name ?: resourceManger.getString(R.string.no_category)
        return TransactionChosenCategory(formattedName, category)
    }

    private fun buildChosenDate(date: LocalDate): TransactionChosenDate {
        return TransactionChosenDate(chosenDateFormatter.format(date), date)
    }

}