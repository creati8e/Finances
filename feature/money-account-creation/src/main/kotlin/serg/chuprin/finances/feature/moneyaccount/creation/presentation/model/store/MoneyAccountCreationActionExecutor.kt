package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.moneyaccount.creation.R
import serg.chuprin.finances.feature.moneyaccount.creation.domain.usecase.CreateMoneyAccountUseCase
import serg.chuprin.finances.feature.moneyaccount.creation.domain.usecase.DeleteMoneyAccountUseCase
import serg.chuprin.finances.feature.moneyaccount.creation.domain.usecase.EditMoneyAccountUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@ScreenScope
class MoneyAccountCreationActionExecutor @Inject constructor(
    private val amountParser: AmountParser,
    private val resourceManger: ResourceManger,
    private val currencyChoiceStore: CurrencyChoiceStore,
    private val screenArguments: MoneyAccountScreenArguments,
    private val editMoneyAccountUseCase: EditMoneyAccountUseCase,
    private val createMoneyAccountUseCase: CreateMoneyAccountUseCase,
    private val deleteMoneyAccountUseCase: DeleteMoneyAccountUseCase
) : StoreActionExecutor<MoneyAccountCreationAction, MoneyAccountCreationState, MoneyAccountCreationEffect, MoneyAccountCreationEvent> {

    override fun invoke(
        action: MoneyAccountCreationAction,
        state: MoneyAccountCreationState,
        eventConsumer: Consumer<MoneyAccountCreationEvent>,
        actionsFlow: Flow<MoneyAccountCreationAction>
    ): Flow<MoneyAccountCreationEffect> {
        return when (action) {
            is MoneyAccountCreationAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    MoneyAccountCreationIntent.BackPress -> {
                        handleBackPressIntent(state, eventConsumer)
                    }
                    MoneyAccountCreationIntent.ClickOnSaveButton -> {
                        handleClickOnSaveButtonIntent(state, eventConsumer)
                    }
                    is MoneyAccountCreationIntent.EnterAccountName -> {
                        handleEnterTitleIntent(intent)
                    }
                    is MoneyAccountCreationIntent.EnterBalance -> {
                        handleEnterAmountIntent(intent)
                    }
                    MoneyAccountCreationIntent.ClickOnDeleteButton -> {
                        handleClickOnDeleteButtonIntent(eventConsumer)
                    }
                    MoneyAccountCreationIntent.ClickOnConfirmAccountDeletion -> {
                        handleClickOnConfirmAccountDeletionIntent(eventConsumer)
                    }
                }
            }
            is MoneyAccountCreationAction.UpdateCurrencyChoiceState -> {
                handleUpdateCurrencyChoiceStateAction(action)
            }
            is MoneyAccountCreationAction.SetInitialState -> {
                handleSetInitialStateForExistingAccountAction(action)
            }
        }
    }

    private fun handleClickOnConfirmAccountDeletionIntent(
        eventConsumer: Consumer<MoneyAccountCreationEvent>
    ): Flow<MoneyAccountCreationEffect> {
        if (screenArguments !is MoneyAccountScreenArguments.Editing) {
            return emptyFlow()
        }
        return flow {
            deleteMoneyAccountUseCase.execute(moneyAccountId = screenArguments.moneyAccountId)
            eventConsumer(MoneyAccountCreationEvent.CloseScreen)
        }
    }

    private fun handleClickOnDeleteButtonIntent(
        eventConsumer: Consumer<MoneyAccountCreationEvent>
    ): Flow<MoneyAccountCreationEffect> {
        return emptyFlowAction {
            eventConsumer(MoneyAccountCreationEvent.ShowAccountDeletionDialog)
        }
    }

    private fun handleSetInitialStateForExistingAccountAction(
        action: MoneyAccountCreationAction.SetInitialState
    ): Flow<MoneyAccountCreationEffect> {
        return flowOfSingleValue {
            MoneyAccountCreationEffect.InitialStateApplied(
                balance = action.balance,
                accountName = action.accountName,
                toolbarTitle = action.toolbarTitle,
                moneyAccountDefaultData = action.moneyAccountDefaultData,
                currencyPickerIsClickable = action.currencyPickerIsClickable,
                accountDeletionButtonIsVisible = action.accountDeletionButtonIsVisible
            )
        }
    }

    private fun handleClickOnSaveButtonIntent(
        state: MoneyAccountCreationState,
        eventConsumer: Consumer<MoneyAccountCreationEvent>
    ): Flow<MoneyAccountCreationEffect> {
        if (state.savingButtonIsEnabled.not()) {
            return emptyFlow()
        }
        val balance = state.balance ?: return emptyFlow()
        val chosenCurrency = state.chosenCurrency ?: return emptyFlow()

        return when (screenArguments) {
            is MoneyAccountScreenArguments.Editing -> {
                flow {
                    editMoneyAccountUseCase.execute(
                        newBalance = balance,
                        newName = state.moneyAccountName,
                        moneyAccountId = screenArguments.moneyAccountId
                    )
                    eventConsumer(
                        MoneyAccountCreationEvent.ShowMessage(
                            resourceManger.getString(R.string.money_account_account_edited_message)
                        )
                    )
                    eventConsumer(MoneyAccountCreationEvent.CloseScreen)
                }
            }
            is MoneyAccountScreenArguments.Creation -> {
                flow {
                    createMoneyAccountUseCase.execute(
                        currency = chosenCurrency,
                        initialBalance = balance,
                        accountName = state.moneyAccountName
                    )
                    eventConsumer(
                        MoneyAccountCreationEvent.ShowMessage(
                            resourceManger.getString(R.string.money_account_creation_account_created_message)
                        )
                    )
                    eventConsumer(MoneyAccountCreationEvent.CloseScreen)
                }
            }
        }
    }

    private fun handleEnterTitleIntent(
        intent: MoneyAccountCreationIntent.EnterAccountName
    ): Flow<MoneyAccountCreationEffect> {
        return flowOfSingleValue {
            MoneyAccountCreationEffect.AccountNameEntered(intent.accountName)
        }
    }

    private fun handleEnterAmountIntent(
        intent: MoneyAccountCreationIntent.EnterBalance
    ): Flow<MoneyAccountCreationEffect> {
        return flowOfSingleValue {
            MoneyAccountCreationEffect.BalanceEntered(amountParser.parse(intent.balance))
        }
    }

    private fun handleBackPressIntent(
        state: MoneyAccountCreationState,
        eventConsumer: Consumer<MoneyAccountCreationEvent>
    ): Flow<MoneyAccountCreationEffect> {
        if (state.currencyPickerIsVisible) {
            return emptyFlowAction {
                currencyChoiceStore.dispatch(CurrencyChoiceIntent.ClickOnCloseCurrencyPicker)
            }
        }
        return emptyFlowAction {
            eventConsumer(MoneyAccountCreationEvent.CloseScreen)
        }
    }

    private fun handleUpdateCurrencyChoiceStateAction(
        action: MoneyAccountCreationAction.UpdateCurrencyChoiceState
    ): Flow<MoneyAccountCreationEffect> {
        return flowOfSingleValue {
            MoneyAccountCreationEffect.CurrencyChoiceStateUpdated(action.state)
        }
    }

}