package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.core.mvi.store.BaseStore
import serg.chuprin.finances.feature.moneyaccount.creation.R
import serg.chuprin.finances.feature.moneyaccount.domain.model.MoneyAccountCreationParams
import serg.chuprin.finances.feature.moneyaccount.domain.model.MoneyAccountEditingParams
import serg.chuprin.finances.feature.moneyaccount.domain.usecase.CreateMoneyAccountUseCase
import serg.chuprin.finances.feature.moneyaccount.domain.usecase.DeleteMoneyAccountUseCase
import serg.chuprin.finances.feature.moneyaccount.domain.usecase.EditMoneyAccountUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@ScreenScope
class MoneyAccountActionExecutor @Inject constructor(
    private val amountParser: AmountParser,
    private val resourceManger: ResourceManger,
    private val currencyChoiceStore: BaseStore<CurrencyChoiceIntent, CurrencyChoiceState, Nothing>,
    private val screenArguments: MoneyAccountScreenArguments,
    private val editMoneyAccountUseCase: EditMoneyAccountUseCase,
    private val createMoneyAccountUseCase: CreateMoneyAccountUseCase,
    private val deleteMoneyAccountUseCase: DeleteMoneyAccountUseCase
) : StoreActionExecutor<MoneyAccountAction, MoneyAccountState, MoneyAccountEffect, MoneyAccountEvent> {

    override fun invoke(
        action: MoneyAccountAction,
        state: MoneyAccountState,
        eventConsumer: Consumer<MoneyAccountEvent>,
        actionsFlow: Flow<MoneyAccountAction>
    ): Flow<MoneyAccountEffect> {
        return when (action) {
            is MoneyAccountAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    MoneyAccountIntent.BackPress -> {
                        handleBackPressIntent(state, eventConsumer)
                    }
                    MoneyAccountIntent.ClickOnSaveButton -> {
                        handleClickOnSaveButtonIntent(state, eventConsumer)
                    }
                    is MoneyAccountIntent.EnterAccountName -> {
                        handleEnterTitleIntent(intent)
                    }
                    is MoneyAccountIntent.EnterBalance -> {
                        handleEnterAmountIntent(intent)
                    }
                    MoneyAccountIntent.ClickOnDeleteButton -> {
                        handleClickOnDeleteButtonIntent(eventConsumer)
                    }
                    MoneyAccountIntent.ClickOnConfirmAccountDeletion -> {
                        handleClickOnConfirmAccountDeletionIntent(eventConsumer)
                    }
                }
            }
            is MoneyAccountAction.UpdateCurrencyChoiceState -> {
                handleUpdateCurrencyChoiceStateAction(action)
            }
            is MoneyAccountAction.SetInitialState -> {
                handleSetInitialStateForExistingAccountAction(action)
            }
        }
    }

    private fun handleClickOnConfirmAccountDeletionIntent(
        eventConsumer: Consumer<MoneyAccountEvent>
    ): Flow<MoneyAccountEffect> {
        if (screenArguments !is MoneyAccountScreenArguments.Editing) {
            return emptyFlow()
        }
        return flow {
            deleteMoneyAccountUseCase.execute(moneyAccountId = screenArguments.moneyAccountId)
            eventConsumer(MoneyAccountEvent.CloseScreen)
        }
    }

    private fun handleClickOnDeleteButtonIntent(
        eventConsumer: Consumer<MoneyAccountEvent>
    ): Flow<MoneyAccountEffect> {
        return emptyFlowAction {
            eventConsumer(MoneyAccountEvent.ShowAccountDeletionDialog)
        }
    }

    private fun handleSetInitialStateForExistingAccountAction(
        action: MoneyAccountAction.SetInitialState
    ): Flow<MoneyAccountEffect> {
        return flowOfSingleValue {
            MoneyAccountEffect.InitialStateApplied(
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
        state: MoneyAccountState,
        eventConsumer: Consumer<MoneyAccountEvent>
    ): Flow<MoneyAccountEffect> {
        if (state.savingButtonIsEnabled.not()) {
            return emptyFlow()
        }
        val balance = state.balance ?: return emptyFlow()
        val chosenCurrency = state.chosenCurrency ?: return emptyFlow()

        return when (screenArguments) {
            is MoneyAccountScreenArguments.Editing -> {
                flow {
                    editMoneyAccountUseCase.execute(
                        MoneyAccountEditingParams(
                            newBalance = balance,
                            newName = state.moneyAccountName,
                            moneyAccountId = screenArguments.moneyAccountId
                        )
                    )
                    eventConsumer(
                        MoneyAccountEvent.ShowMessage(
                            resourceManger.getString(R.string.money_account_account_edited_message)
                        )
                    )
                    eventConsumer(MoneyAccountEvent.CloseScreen)
                }
            }
            is MoneyAccountScreenArguments.Creation -> {
                flow {
                    createMoneyAccountUseCase.execute(
                        MoneyAccountCreationParams(
                            currency = chosenCurrency,
                            initialBalance = balance,
                            accountName = state.moneyAccountName
                        )
                    )
                    eventConsumer(
                        MoneyAccountEvent.ShowMessage(
                            resourceManger.getString(R.string.money_account_creation_account_created_message)
                        )
                    )
                    eventConsumer(MoneyAccountEvent.CloseScreen)
                }
            }
        }
    }

    private fun handleEnterTitleIntent(
        intent: MoneyAccountIntent.EnterAccountName
    ): Flow<MoneyAccountEffect> {
        return flowOfSingleValue {
            MoneyAccountEffect.AccountNameEntered(intent.accountName)
        }
    }

    private fun handleEnterAmountIntent(
        intent: MoneyAccountIntent.EnterBalance
    ): Flow<MoneyAccountEffect> {
        return flowOfSingleValue {
            MoneyAccountEffect.BalanceEntered(amountParser.parse(intent.balance))
        }
    }

    private fun handleBackPressIntent(
        state: MoneyAccountState,
        eventConsumer: Consumer<MoneyAccountEvent>
    ): Flow<MoneyAccountEffect> {
        if (state.currencyPickerIsVisible) {
            return emptyFlowAction {
                currencyChoiceStore.dispatch(CurrencyChoiceIntent.ClickOnCloseCurrencyPicker)
            }
        }
        return emptyFlowAction {
            eventConsumer(MoneyAccountEvent.CloseScreen)
        }
    }

    private fun handleUpdateCurrencyChoiceStateAction(
        action: MoneyAccountAction.UpdateCurrencyChoiceState
    ): Flow<MoneyAccountEffect> {
        return flowOfSingleValue {
            MoneyAccountEffect.CurrencyChoiceStateUpdated(action.state)
        }
    }

}