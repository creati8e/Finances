package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingEffect
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingFinalStepBuilder
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingStepState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class AccountsSetupBalanceEnterStepIntentExecutor @Inject constructor(
    private val amountParser: AmountParser,
    private val finalStepBuilder: AccountsSetupOnboardingFinalStepBuilder,
    private val onboardingCompletionExecutor: AccountsSetupOnboardingCompletionExecutor
) {

    fun handleEnterBalanceIntent(
        intent: AccountsSetupOnboardingIntent.EnterBalance
    ): Flow<AccountsSetupOnboardingEffect> {
        return flowOfSingleValue {
            AccountsSetupOnboardingEffect.BalanceEntered(
                acceptButtonIsEnabled = true,
                balance = amountParser.parse(intent.enteredBalance)
            )
        }
    }

    fun handleClickOnAcceptBalanceButtonIntent(
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        return when (val stepState = state.stepState) {
            is AccountsSetupOnboardingStepState.CashBalanceEnter -> {
                acceptBalanceInCashState(stepState)
            }
            is AccountsSetupOnboardingStepState.BankCardBalanceEnter -> {
                acceptBalanceInBankCardState(stepState, state)
            }
            AccountsSetupOnboardingStepState.CashQuestion,
            AccountsSetupOnboardingStepState.BankCardQuestion,
            is AccountsSetupOnboardingStepState.EverythingIsSetUp -> {
                // Unreachable branch.
                emptyFlow()
            }
        }
    }

    private fun acceptBalanceInBankCardState(
        stepState: AccountsSetupOnboardingStepState.BankCardBalanceEnter,
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        return flow {
            @Suppress("UnnecessaryVariable")
            val bankCardBalance = stepState.balance

            onboardingCompletionExecutor.completeOnboarding(
                cashBalance = state.cashBalance,
                bankCardBalance = bankCardBalance
            )
            emit(
                AccountsSetupOnboardingEffect.AccountBalanceAccepted(
                    // Use cash balance from previous step.
                    cashBalance = state.cashBalance,
                    bankCardBalance = bankCardBalance
                )
            )
            emit(
                AccountsSetupOnboardingEffect.StepChanged(
                    finalStepBuilder.build(
                        cashBalance = state.cashBalance,
                        bankCardBalance = state.bankCardBalance
                    )
                )
            )
        }
    }

    private fun acceptBalanceInCashState(
        stepState: AccountsSetupOnboardingStepState.CashBalanceEnter
    ): Flow<AccountsSetupOnboardingEffect> {
        return flowOf(
            AccountsSetupOnboardingEffect.AccountBalanceAccepted(
                // Bank card balance is not entered on this step yet.
                bankCardBalance = null,
                cashBalance = stepState.balance
            ),
            AccountsSetupOnboardingEffect.StepChanged(
                AccountsSetupOnboardingStepState.BankCardQuestion
            )
        )
    }

}