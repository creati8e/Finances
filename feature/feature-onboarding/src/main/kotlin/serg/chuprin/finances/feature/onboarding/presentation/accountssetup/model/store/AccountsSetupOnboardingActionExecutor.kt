package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import androidx.core.util.Consumer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.AccountBalance
import serg.chuprin.finances.core.api.extensions.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingActionExecutor @Inject constructor(
    private val amountParser: AmountParser,
    private val amountFormatter: AmountFormatter
) : StoreActionExecutor<AccountsSetupOnboardingIntent, AccountsSetupOnboardingState, AccountsSetupOnboardingEffect, AccountsSetupOnboardingEvent> {

    override fun invoke(
        intent: AccountsSetupOnboardingIntent,
        state: AccountsSetupOnboardingState,
        eventConsumer: Consumer<AccountsSetupOnboardingEvent>,
        actionsFlow: Flow<AccountsSetupOnboardingIntent>
    ): Flow<AccountsSetupOnboardingEffect> {
        return when (intent) {
            AccountsSetupOnboardingIntent.ClickOnPositiveButton -> {
                handleClickOnPositiveButtonIntent(state)
            }
            AccountsSetupOnboardingIntent.ClickOnNegativeButton -> {
                handleClickOnNegativeButtonIntent(state)
            }
            is AccountsSetupOnboardingIntent.ClickOnAcceptBalanceButton -> {
                handleClickOnAcceptBalanceButtonIntent(intent, state)
            }
            is AccountsSetupOnboardingIntent.InputAmount -> {
                handleInputAmountIntent(intent)
            }
        }
    }

    private fun handleInputAmountIntent(
        intent: AccountsSetupOnboardingIntent.InputAmount
    ): Flow<AccountsSetupOnboardingEffect> {
        return flowOfSingleValue {
            // TODO: Use currency from onboarding.
            val formattedAmount = amountFormatter.formatInput(
                intent.enteredAmount,
                Currency.getInstance(Locale.getDefault())
            )
            AccountsSetupOnboardingEffect.AmountEntered(
                acceptButtonIsVisible = true,
                formattedAmount = formattedAmount
            )
        }
    }

    private fun handleClickOnAcceptBalanceButtonIntent(
        intent: AccountsSetupOnboardingIntent.ClickOnAcceptBalanceButton,
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        return when (val stepState = state.stepState) {
            is AccountsSetupOnboardingStepState.CashAmountEnter -> {
                // TODO: Think about failed parsing.
                val nextStepState = AccountsSetupOnboardingStepState.BankCardQuestion
                flowOf(
                    AccountsSetupOnboardingEffect.AccountBalanceEntered(
                        // Bank card balance not entered on this step yet.
                        bankCardBalance = null,
                        cashBalance = AccountBalance(amountParser.parse(stepState.enteredAmount)!!)
                    ),
                    AccountsSetupOnboardingEffect.StepChanged(nextStepState)
                )
            }
            is AccountsSetupOnboardingStepState.BankCardAmountEnter -> {
                // TODO: Think about failed parsing.
                val nextState = AccountsSetupOnboardingStepState.EverythingIsSetUp
                flowOf(
                    AccountsSetupOnboardingEffect.AccountBalanceEntered(
                        // Use cache balance from previous step.
                        cashBalance = state.cashBalance,
                        bankCardBalance = AccountBalance(amountParser.parse(stepState.enteredAmount)!!)
                    ),
                    AccountsSetupOnboardingEffect.StepChanged(nextState)
                )
            }
            AccountsSetupOnboardingStepState.CashQuestion,
            AccountsSetupOnboardingStepState.BankCardQuestion,
            AccountsSetupOnboardingStepState.EverythingIsSetUp -> {
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
            AccountsSetupOnboardingStepState.EverythingIsSetUp,
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
                // TODO: Implement logic if not a single bank account was set up.
                val stepState = AccountsSetupOnboardingStepState.EverythingIsSetUp
                flowOf(AccountsSetupOnboardingEffect.StepChanged(stepState))
            }
            AccountsSetupOnboardingStepState.EverythingIsSetUp,
            is AccountsSetupOnboardingStepState.CashAmountEnter,
            is AccountsSetupOnboardingStepState.BankCardAmountEnter -> {
                // Unreachable state.
                emptyFlow()
            }
        }
    }

}