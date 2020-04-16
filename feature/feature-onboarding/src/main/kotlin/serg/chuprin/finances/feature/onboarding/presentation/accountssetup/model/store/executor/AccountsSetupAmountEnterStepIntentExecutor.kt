package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.MoneyAccountBalance
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.model.AmountInputState
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingEffect
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingFinalStepBuilder
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class AccountsSetupAmountEnterStepIntentExecutor @Inject constructor(
    private val amountParser: AmountParser,
    private val amountFormatter: AmountFormatter,
    private val finalStepBuilder: AccountsSetupOnboardingFinalStepBuilder,
    private val onboardingCompletionExecutor: AccountsSetupOnboardingCompletionExecutor
) {

    fun handleInputAmountIntent(
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

    fun handleClickOnAcceptBalanceButtonIntent(
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        return when (val stepState = state.stepState) {
            is AccountsSetupOnboardingStepState.CashAmountEnter -> {
                acceptBalanceInCashAmountEnterState(stepState)
            }
            is AccountsSetupOnboardingStepState.BankCardAmountEnter -> {
                acceptBalanceInBankCardAmountEnterState(stepState, state)
            }
            AccountsSetupOnboardingStepState.CashQuestion,
            AccountsSetupOnboardingStepState.BankCardQuestion,
            is AccountsSetupOnboardingStepState.EverythingIsSetUp -> {
                // Unreachable branch.
                emptyFlow()
            }
        }
    }

    private fun acceptBalanceInBankCardAmountEnterState(
        stepState: AccountsSetupOnboardingStepState.BankCardAmountEnter,
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        val amount = stepState.amountInputState.formattedAmount
        val parsedAmount = amountParser.parse(amount)
            ?: return flowOf(
                AccountsSetupOnboardingEffect.EnteredAmountParsedWithError(
                    stepState.copy(
                        acceptAmountButtonIsEnabled = false,
                        amountInputState = AmountInputState(
                            hasError = true,
                            formattedAmount = amount
                        )
                    )
                )
            )
        return flow {
            val bankCardBalance = MoneyAccountBalance(parsedAmount)

            onboardingCompletionExecutor.completeOnboarding(
                cashBalance = state.cashBalance,
                bankCardBalance = bankCardBalance
            )
            emit(
                AccountsSetupOnboardingEffect.AccountBalanceEntered(
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

    private fun acceptBalanceInCashAmountEnterState(
        stepState: AccountsSetupOnboardingStepState.CashAmountEnter
    ): Flow<AccountsSetupOnboardingEffect> {
        val amount = stepState.amountInputState.formattedAmount
        val parsedAmount = amountParser.parse(amount)
            ?: return flowOf(
                AccountsSetupOnboardingEffect.EnteredAmountParsedWithError(
                    stepState.copy(
                        acceptAmountButtonIsEnabled = false,
                        amountInputState = AmountInputState(
                            hasError = true,
                            formattedAmount = amount
                        )
                    )
                )
            )
        return flowOf(
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