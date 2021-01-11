package serg.chuprin.finances.feature.transaction.presentation.model.store

import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.formatter.CategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.transaction.R
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import serg.chuprin.finances.feature.transaction.domain.usecase.CreateTransactionUseCase
import serg.chuprin.finances.feature.transaction.domain.usecase.DeleteTransactionUseCase
import serg.chuprin.finances.feature.transaction.domain.usecase.EditTransactionUseCase
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenCategory
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenDate
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenMoneyAccount
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionDefaultData
import serg.chuprin.finances.feature.transaction.presentation.model.formatter.TransactionChosenDateFormatter
import java.time.LocalDate
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionActionExecutor @Inject constructor(
    private val amountParser: AmountParser,
    private val resourceManger: ResourceManger,
    private val screenArguments: TransactionScreenArguments,

    // region Use cases.

    private val editTransactionUseCase: EditTransactionUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,

    // endregion

    // region Repositories.

    private val moneyAccountRepository: MoneyAccountRepository,
    private val categoryRepository: CategoryRepository,

    // endregion

    // region Formatters.

    private val chosenDateFormatter: TransactionChosenDateFormatter,
    private val categoryNameFormatter: CategoryWithParentFormatter

    // endregion
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
                        handleEnterAmountIntent(intent)
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
                    TransactionIntent.ClickOnDeleteTransaction -> {
                        handleClickOnDeleteTransactionIntent(eventConsumer)
                    }
                    TransactionIntent.ClickOnConfirmTransactionDeletion -> {
                        handleClickOnConfirmTransactionDeletionIntent(eventConsumer)
                    }
                }
            }
            is TransactionAction.FormatInitialState -> {
                handleFormatInitialStateAction(action)
            }
            is TransactionAction.FormatInitialStateForExistingTransaction -> {
                handleFormatInitialStateForExistingTransactionAction(action, state)
            }
        }
    }

    private fun handleClickOnConfirmTransactionDeletionIntent(
        eventConsumer: Consumer<TransactionEvent>
    ): Flow<TransactionEffect> {
        if (screenArguments !is TransactionScreenArguments.Editing) {
            return emptyFlow()
        }
        return flow {
            deleteTransactionUseCase.execute(screenArguments.transactionId)
            eventConsumer(TransactionEvent.CloseScreen)
        }
    }

    private fun handleClickOnDeleteTransactionIntent(
        eventConsumer: Consumer<TransactionEvent>
    ): Flow<TransactionEffect> {
        if (screenArguments !is TransactionScreenArguments.Editing) {
            return emptyFlow()
        }
        return emptyFlowAction {
            eventConsumer(TransactionEvent.ShowTransactionDeletionDialog)
        }
    }

    // region Actions.

    private fun handleFormatInitialStateForExistingTransactionAction(
        action: TransactionAction.FormatInitialStateForExistingTransaction,
        state: TransactionState
    ): Flow<TransactionEffect> {
        return flowOfSingleValue {
            val chosenMoneyAccount = getMoneyAccount(action.moneyAccountId)
            val chosenCategory = getCategory(
                state = state,
                userId = action.userId,
                categoryId = action.categoryId
            )
            TransactionEffect.InitialStateFormatted(
                transactionDefaultData = TransactionDefaultData(
                    dateTime = action.dateTime,
                    operation = action.operation,
                    enteredAmount = action.amount,
                    category = chosenCategory.category,
                    moneyAccount = chosenMoneyAccount.account
                ),
                userId = action.userId,
                operation = action.operation,
                enteredAmount = action.amount,
                chosenCategory = chosenCategory,
                chosenMoneyAccount = chosenMoneyAccount,
                transactionDeletionButtonIsVisible = true,
                chosenDate = formatChosenDate(action.dateTime.toLocalDate())
            )
        }
    }

    private fun handleFormatInitialStateAction(
        action: TransactionAction.FormatInitialState
    ): Flow<TransactionEffect> {
        return flowOf(
            TransactionEffect.InitialStateFormatted(
                userId = action.userId,
                enteredAmount = action.amount,
                operation = action.operation,
                transactionDefaultData = null,
                chosenDate = formatChosenDate(action.date),
                transactionDeletionButtonIsVisible = false,
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
                val messageStringRes = if (screenArguments is TransactionScreenArguments.Editing) {
                    R.string.transaction_unsaved_changes_dialog_message
                } else {
                    R.string.transaction_unsaved_transaction_dialog_message
                }
                eventConsumer(
                    TransactionEvent.ShowUnsavedChangedDialog(
                        resourceManger.getString(messageStringRes)
                    )
                )

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
                getCategory(categoryId = intent.categoryId, userId = state.userId, state = state)
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
                PlainTransactionType.EXPENSE -> CategoryType.EXPENSE
                PlainTransactionType.INCOME -> CategoryType.INCOME
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
        val amount = state.enteredAmount ?: return emptyFlow()
        return when (screenArguments) {
            is TransactionScreenArguments.Editing -> {
                flow {
                    editTransactionUseCase.execute(
                        amount = amount,
                        ownerId = state.userId,
                        operation = state.operation,
                        newDate = state.chosenDate.localDate,
                        category = state.chosenCategory.category,
                        transactionId = screenArguments.transactionId,
                        moneyAccount = state.chosenMoneyAccount.account,
                        existingDateTime = state.transactionDefaultData!!.dateTime
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
        intent: TransactionIntent.EnterAmount
    ): Flow<TransactionEffect> {
        return flowOfSingleValue {
            TransactionEffect.AmountEntered(enteredAmount = amountParser.parse(intent.amount))
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
        state: TransactionState
    ): TransactionChosenCategory {
        if (categoryId == null) {
            return formatChosenCategory(categoryWithParent = null)
        }
        val categories = categoryRepository
            .categories(
                CategoriesQuery(
                    ownerId = userId,
                    categoryIds = setOf(categoryId),
                    relation = CategoriesQuery.Relation.RETRIEVE_PARENTS
                )
            )
            .values

        val categoryWithParent = categories
            .firstOrNull { it.parentCategory != null }
            ?: categories.first()

        // Check if chosen category has the same type as current transaction operation
        // (i.e if operation is expense, category must be expense too).
        return when (state.operation) {
            is TransactionChosenOperation.Plain -> {
                if (categoryWithParent.category.type == state.operation.type.toCategoryType()) {
                    formatChosenCategory(categoryWithParent)
                } else {
                    formatChosenCategory(categoryWithParent = null)
                }
            }
        }
    }

    private fun formatChosenCategory(
        categoryWithParent: CategoryWithParent?
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