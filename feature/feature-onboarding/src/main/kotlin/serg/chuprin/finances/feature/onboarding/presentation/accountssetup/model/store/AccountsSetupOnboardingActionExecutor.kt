package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.MoneyAccountBalance
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.model.AmountInputState
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.domain.OnboardingMoneyAccountCreationParams
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteAccountsSetupOnboardingUseCase
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingAction
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingActionExecutor @Inject constructor(
    private val amountParser: AmountParser,
    private val resourceManger: ResourceManger,
    private val amountFormatter: AmountFormatter,
    private val completeOnboardingUseCase: CompleteAccountsSetupOnboardingUseCase
) : StoreActionExecutor<AccountsSetupOnboardingAction, AccountsSetupOnboardingState, AccountsSetupOnboardingEffect, AccountsSetupOnboardingEvent> {

    override fun invoke(
        action: AccountsSetupOnboardingAction,
        state: AccountsSetupOnboardingState,
        eventConsumer: Consumer<AccountsSetupOnboardingEvent>,
        actionsFlow: Flow<AccountsSetupOnboardingAction>
    ): Flow<AccountsSetupOnboardingEffect> {
        return when (action) {
            is AccountsSetupOnboardingAction.SetCurrency -> {
                handleSetCurrencyAction(action)
            }
            is AccountsSetupOnboardingAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    AccountsSetupOnboardingIntent.ClickOnPositiveButton -> {
                        handleClickOnPositiveButtonIntent(state)
                    }
                    AccountsSetupOnboardingIntent.ClickOnNegativeButton -> {
                        handleClickOnNegativeButtonIntent(state)
                    }
                    is AccountsSetupOnboardingIntent.ClickOnAcceptBalanceButton -> {
                        handleClickOnAcceptBalanceButtonIntent(state)
                    }
                    is AccountsSetupOnboardingIntent.InputAmount -> {
                        handleInputAmountIntent(intent, state)
                    }
                    AccountsSetupOnboardingIntent.ClickOnStartUsingAppButton -> {
                        handleClickOnStartUsingAppButton(state, eventConsumer)
                    }
                }
            }
        }
    }

    private fun handleSetCurrencyAction(
        action: AccountsSetupOnboardingAction.SetCurrency
    ): Flow<AccountsSetupOnboardingEffect> {
        return flowOf(AccountsSetupOnboardingEffect.CurrencyIsSet(action.currency))
    }

    private fun handleClickOnStartUsingAppButton(
        state: AccountsSetupOnboardingState,
        eventConsumer: Consumer<AccountsSetupOnboardingEvent>
    ): Flow<AccountsSetupOnboardingEffect> {
        if (state.stepState is AccountsSetupOnboardingStepState.EverythingIsSetUp) {
            return emptyFlowAction {
                eventConsumer(AccountsSetupOnboardingEvent.NavigateToDashboard)
            }
        }
        return emptyFlow()
    }

    private fun handleInputAmountIntent(
        intent: AccountsSetupOnboardingIntent.InputAmount,
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        return flowOfSingleValue {
            val formattedAmount = amountFormatter.formatInput(
                currency = state.currency,
                input = intent.enteredAmount
            )
            AccountsSetupOnboardingEffect.AmountEntered(
                acceptButtonIsVisible = true,
                amountInputState = AmountInputState(false, formattedAmount)
            )
        }
    }

    private fun handleClickOnAcceptBalanceButtonIntent(
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        return when (val stepState = state.stepState) {
            is AccountsSetupOnboardingStepState.CashAmountEnter -> {
                val amount = stepState.amountInputState.formattedAmount
                val parsedAmount = amountParser.parse(amount)
                if (parsedAmount == null) {
                    flowOf(
                        AccountsSetupOnboardingEffect.EnteredAmountParsedWithError(
                            stepState.copy(
                                acceptAmountButtonIsEnabled = false,
                                amountInputState = AmountInputState(true, amount)
                            )
                        )
                    )
                } else {
                    flowOf(
                        AccountsSetupOnboardingEffect.AccountBalanceEntered(
                            // Bank card balance not entered on this step yet.
                            bankCardBalance = null,
                            cashBalance = MoneyAccountBalance(parsedAmount)
                        ),
                        AccountsSetupOnboardingEffect.StepChanged(
                            AccountsSetupOnboardingStepState.BankCardQuestion
                        )
                    )
                }
            }
            is AccountsSetupOnboardingStepState.BankCardAmountEnter -> {
                val amount = stepState.amountInputState.formattedAmount
                val parsedAmount = amountParser.parse(amount)
                if (parsedAmount == null) {
                    flowOf(
                        AccountsSetupOnboardingEffect.EnteredAmountParsedWithError(
                            stepState.copy(
                                acceptAmountButtonIsEnabled = false,
                                amountInputState = AmountInputState(true, amount)
                            )
                        )
                    )
                } else {
                    flow {
                        completeOnboarding(state)
                        emit(
                            AccountsSetupOnboardingEffect.AccountBalanceEntered(
                                // Use cash balance from previous step.
                                cashBalance = state.cashBalance,
                                bankCardBalance = MoneyAccountBalance(parsedAmount)
                            )
                        )
                        emit(AccountsSetupOnboardingEffect.StepChanged(buildFinalStepState(state)))
                    }
                }
            }
            AccountsSetupOnboardingStepState.CashQuestion,
            AccountsSetupOnboardingStepState.BankCardQuestion,
            is AccountsSetupOnboardingStepState.EverythingIsSetUp -> {
                // Unreachable branch.
                emptyFlow()
            }
        }
    }

    private fun handleClickOnPositiveButtonIntent(
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        return when (state.stepState) {
            AccountsSetupOnboardingStepState.CashQuestion -> {
                val stepState = AccountsSetupOnboardingStepState.CashAmountEnter()
                flowOf(AccountsSetupOnboardingEffect.StepChanged(stepState))
            }
            AccountsSetupOnboardingStepState.BankCardQuestion -> {
                val stepState = AccountsSetupOnboardingStepState.BankCardAmountEnter()
                flowOf(AccountsSetupOnboardingEffect.StepChanged(stepState))
            }
            is AccountsSetupOnboardingStepState.EverythingIsSetUp,
            is AccountsSetupOnboardingStepState.CashAmountEnter,
            is AccountsSetupOnboardingStepState.BankCardAmountEnter -> {
                // Unreachable state.
                emptyFlow()
            }
        }
    }

    private fun handleClickOnNegativeButtonIntent(
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        return when (state.stepState) {
            AccountsSetupOnboardingStepState.CashQuestion -> {
                val stepState = AccountsSetupOnboardingStepState.BankCardQuestion
                flowOf(AccountsSetupOnboardingEffect.StepChanged(stepState))
            }
            AccountsSetupOnboardingStepState.BankCardQuestion -> {
                flow {
                    completeOnboarding(state)
                    emit(AccountsSetupOnboardingEffect.StepChanged(buildFinalStepState(state)))
                }
            }
            is AccountsSetupOnboardingStepState.EverythingIsSetUp,
            is AccountsSetupOnboardingStepState.CashAmountEnter,
            is AccountsSetupOnboardingStepState.BankCardAmountEnter -> {
                // Unreachable state.
                emptyFlow()
            }
        }
    }

    private suspend fun completeOnboarding(state: AccountsSetupOnboardingState) {
        val bankCardAccountCreationParams = state.bankCardBalance?.let { balance ->
            val name = getString(R.string.bank_card_money_account_default_name)
            OnboardingMoneyAccountCreationParams(name, balance)
        }
        val cashAccountCreationParams = state.cashBalance?.let { balance ->
            val name = getString(R.string.cash_money_account_default_name)
            OnboardingMoneyAccountCreationParams(name, balance)
        }
        completeOnboardingUseCase.execute(
            cashAccountParams = cashAccountCreationParams,
            bankAccountCardParams = bankCardAccountCreationParams
        )
    }

    private fun buildFinalStepState(
        state: AccountsSetupOnboardingState
    ): AccountsSetupOnboardingStepState {
        val message = if (state.bankCardBalance == null && state.cashBalance == null) {
            // User didn't setup any account.
            getString(R.string.onboarding_accounts_setup_subtitle_no_accounts)
        } else {
            getString(R.string.onboarding_accounts_setup_subtitle_setup_finished)
        }
        return AccountsSetupOnboardingStepState.EverythingIsSetUp(message)
    }

    private fun getString(@StringRes stringRes: Int): String {
        return resourceManger.getString(stringRes)
    }

}