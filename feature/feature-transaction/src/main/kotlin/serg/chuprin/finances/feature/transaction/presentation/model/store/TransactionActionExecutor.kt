package serg.chuprin.finances.feature.transaction.presentation.model.store

import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.category.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.TransactionCategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import serg.chuprin.finances.feature.transaction.domain.usecase.CreateTransactionUseCase
import serg.chuprin.finances.feature.transaction.domain.usecase.EditTransactionUseCase
import serg.chuprin.finances.feature.transaction.presentation.model.*
import serg.chuprin.finances.feature.transaction.presentation.model.formatter.TransactionChosenDateFormatter
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionActionExecutor @Inject constructor(
    private val amountParser: AmountParser,
    private val amountFormatter: AmountFormatter,
    private val screenArguments: TransactionScreenArguments,
    private val moneyAccountRepository: MoneyAccountRepository,
    private val editTransactionUseCase: EditTransactionUseCase,
    private val categoryRepository: TransactionCategoryRepository,
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val chosenDateFormatter: TransactionChosenDateFormatter,
    private val categoryNameFormatter: TransactionCategoryWithParentFormatter
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
                        handleClickOnSaveButtonIntent(state, eventConsumer)
                    }
                    TransactionIntent.ClickOnCloseButton -> {
                        handleClickOnCloseButtonIntent(state, eventConsumer)
                    }
                    TransactionIntent.ClickOnCategory -> {
                        handleClickOnCategoryIntent(state, eventConsumer)
                    }
                    is TransactionIntent.ChooseCategory -> {
                        handleChooseCategoryIntent(intent, state)
                    }
                    is TransactionIntent.ClickOnOperationType -> {
                        handleClickOnOperationTypeIntent(intent, state)
                    }
                    TransactionIntent.ClickOnDate -> {
                        handleClickOnDate(state, eventConsumer)
                    }
                    is TransactionIntent.ChooseDate -> {
                        handleChooseDateIntent(intent)
                    }
                    TransactionIntent.ClickOnMoneyAccount -> {
                        handleClickOnMoneyAccountIntent(eventConsumer)
                    }
                    is TransactionIntent.ChooseMoneyAccount -> {
                        handleChooseMoneyAccountIntent(intent, state)
                    }
                    TransactionIntent.ClickOnUnsavedChangedDialogNegativeButton -> {
                        handleClickOnUnsavedChangedDialogNegativeButtonIntent(eventConsumer)
                    }
                }
            }
            is TransactionAction.FormatInitialState -> {
                handleFormatInitialStateAction(action)
            }
            is TransactionAction.FormatInitialStateForExistingTransaction -> {
                handleFormatInitialStateForExistingTransactionAction(action)
            }
        }
    }

    // region Actions.

    private fun handleFormatInitialStateForExistingTransactionAction(
        action: TransactionAction.FormatInitialStateForExistingTransaction
    ): Flow<TransactionEffect> {
        return flowOfSingleValue {
            val chosenMoneyAccount = getMoneyAccount(action.moneyAccountId)
            val enteredAmount = buildEnteredAmount(
                amountString = action.amount.toString(),
                currency = chosenMoneyAccount.account.currency
            )
            val chosenCategory = getCategory(
                userId = action.userId,
                categoryId = action.categoryId
            )
            val chosenDate = formatChosenDate(action.date)
            TransactionEffect.InitialStateFormatted(
                transactionDefaultData = TransactionDefaultData(
                    chosenDate = chosenDate,
                    operation = action.operation,
                    enteredAmount = enteredAmount,
                    chosenCategory = chosenCategory,
                    chosenMoneyAccount = chosenMoneyAccount
                ),
                chosenDate = chosenDate,
                userId = action.userId,
                operation = action.operation,
                enteredAmount = enteredAmount,
                chosenCategory = chosenCategory,
                chosenMoneyAccount = chosenMoneyAccount

            )
        }
    }

    private fun handleFormatInitialStateAction(
        action: TransactionAction.FormatInitialState
    ): Flow<TransactionEffect> {
        return flowOf(
            TransactionEffect.InitialStateFormatted(
                enteredAmount = buildEnteredAmount(
                    amountString = action.amount.toString(),
                    currency = Currency.getInstance(Locale.getDefault())
                ),
                userId = action.userId,
                operation = action.operation,
                transactionDefaultData = null,
                chosenDate = formatChosenDate(action.date),
                chosenCategory = formatChosenCategory(action.category),
                chosenMoneyAccount = TransactionChosenMoneyAccount(
                    account = action.moneyAccount,
                    formattedName = action.moneyAccount.name
                )
            )
        )
    }

    // endregion


    // region Intents.

    private fun handleClickOnUnsavedChangedDialogNegativeButtonIntent(
        eventConsumer: Consumer<TransactionEvent>
    ): Flow<TransactionEffect> {
        return emptyFlowAction {
            eventConsumer(TransactionEvent.CloseScreen)
        }
    }

    private fun handleChooseMoneyAccountIntent(
        intent: TransactionIntent.ChooseMoneyAccount,
        state: TransactionState
    ): Flow<TransactionEffect> {
        if (intent.moneyAccountId == state.chosenMoneyAccount.account.id) {
            return emptyFlow()
        }
        return flowOfSingleValue {
            TransactionEffect.MoneyAccountChanged(getMoneyAccount(intent.moneyAccountId))
        }
    }

    private fun handleClickOnMoneyAccountIntent(
        eventConsumer: Consumer<TransactionEvent>
    ): Flow<TransactionEffect> {
        return emptyFlowAction {
            val screenArguments = MoneyAccountsListScreenArguments.Picker
            eventConsumer(TransactionEvent.NavigateToMoneyAccountPickerScreen(screenArguments))
        }
    }

    private fun handleChooseDateIntent(
        intent: TransactionIntent.ChooseDate
    ): Flow<TransactionEffect> {
        return flowOf(TransactionEffect.DateChanged(formatChosenDate(intent.localDate)))
    }

    private fun handleClickOnDate(
        state: TransactionState,
        eventConsumer: Consumer<TransactionEvent>
    ): Flow<TransactionEffect> {
        return emptyFlowAction {
            eventConsumer(TransactionEvent.ShowDatePicker(state.chosenDate.localDate))
        }
    }

    private fun handleClickOnCloseButtonIntent(
        state: TransactionState,
        eventConsumer: Consumer<TransactionEvent>
    ): Flow<TransactionEffect> {
        return emptyFlowAction {
            if (state.saveButtonIsEnabled) {
                eventConsumer(TransactionEvent.ShowUnsavedChangedDialog)
            } else {
                eventConsumer(TransactionEvent.CloseScreen)
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
        if (state.chosenCategory.category != null) {
            return flowOf(TransactionEffect.CategoryChanged(formatChosenCategory(null)), effect)
        }
        return flowOf(effect)
    }

    private fun handleChooseCategoryIntent(
        intent: TransactionIntent.ChooseCategory,
        state: TransactionState
    ): Flow<TransactionEffect> {
        if (intent.categoryId == state.chosenCategory.category?.id) {
            return emptyFlow()
        }
        return flowOfSingleValue {
            TransactionEffect.CategoryChanged(
                getCategory(categoryId = intent.categoryId, userId = state.userId)
            )
        }
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

    private fun handleClickOnSaveButtonIntent(
        state: TransactionState,
        eventConsumer: Consumer<TransactionEvent>
    ): Flow<TransactionEffect> {
        val amount = state.enteredAmount.amount ?: return emptyFlow()
        return when (screenArguments) {
            is TransactionScreenArguments.Editing -> {
                flow {
                    editTransactionUseCase.execute(
                        amount = amount,
                        ownerId = state.userId,
                        operation = state.operation,
                        date = state.chosenDate.localDate,
                        category = state.chosenCategory.category,
                        transactionId = screenArguments.transactionId,
                        moneyAccount = state.chosenMoneyAccount.account
                    )
                    eventConsumer(TransactionEvent.CloseScreen)
                }
            }
            is TransactionScreenArguments.Creation -> {
                emptyFlowAction {
                    createTransactionUseCase.execute(
                        amount = amount,
                        ownerId = state.userId,
                        operation = state.operation,
                        date = state.chosenDate.localDate,
                        category = state.chosenCategory.category,
                        moneyAccount = state.chosenMoneyAccount.account
                    )
                    eventConsumer(TransactionEvent.CloseScreen)
                }
            }
        }
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
            val enteredAmount = buildEnteredAmount(
                amountString = intent.amount,
                currency = state.chosenMoneyAccount.account.currency
            )
            TransactionEffect.AmountEntered(enteredAmount = enteredAmount)
        }
    }

    // endregion

    private suspend fun getMoneyAccount(moneyAccountId: Id): TransactionChosenMoneyAccount {
        val moneyAccount = moneyAccountRepository.accountFlow(moneyAccountId).first()!!
        return TransactionChosenMoneyAccount(moneyAccount.name, moneyAccount)
    }

    private suspend fun getCategory(
        categoryId: Id?,
        userId: Id,
    ): TransactionChosenCategory {
        if (categoryId == null) {
            return formatChosenCategory(categoryWithParent = null)
        }
        val categories = categoryRepository
            .categories(
                TransactionCategoriesQuery(
                    ownerId = userId,
                    categoryIds = setOf(categoryId),
                    relation = TransactionCategoriesQuery.Relation.RETRIEVE_PARENTS
                )
            )
            .values

        val categoryWithParent = categories
            .firstOrNull { it.parentCategory != null }
            ?: categories.first()

        return formatChosenCategory(categoryWithParent)
    }

    private fun buildEnteredAmount(
        amountString: String,
        currency: Currency
    ): TransactionEnteredAmount {
        val formattedAmount = amountFormatter.formatInput(
            input = amountString,
            currency = currency
        )
        val parsedAmount = amountParser.parse(formattedAmount)
        return TransactionEnteredAmount(
            amount = parsedAmount,
            formatted = formattedAmount,
            hasError = parsedAmount == null
        )
    }

    private fun formatChosenCategory(
        categoryWithParent: TransactionCategoryWithParent?
    ): TransactionChosenCategory {
        return TransactionChosenCategory(
            category = categoryWithParent?.category,
            formattedName = categoryNameFormatter.format(categoryWithParent, transaction = null)
        )
    }

    private fun formatChosenDate(date: LocalDate): TransactionChosenDate {
        return TransactionChosenDate(chosenDateFormatter.format(date), date)
    }

}