package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingEffect
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingFinalStepBuilder
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingStepState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class AccountsSetupQuestionStateIntentExecutor @Inject constructor(
    private val finalStepBuilder: AccountsSetupOnboardingFinalStepBuilder,
    private val onboardingCompletionExecutor: AccountsSetupOnboardingCompletionExecutor
) {

    fun handleClickOnPositiveButtonIntent(
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        return when (state.stepState) {
            AccountsSetupOnboardingStepState.CashQuestion -> {
                val stepState = AccountsSetupOnboardingStepState.CashBalanceEnter()
                flowOf(AccountsSetupOnboardingEffect.StepChanged(stepState))
            }
            AccountsSetupOnboardingStepState.BankCardQuestion -> {
                val stepState = AccountsSetupOnboardingStepState.BankCardBalanceEnter()
                flowOf(AccountsSetupOnboardingEffect.StepChanged(stepState))
            }
            is AccountsSetupOnboardingStepState.EverythingIsSetUp,
            is AccountsSetupOnboardingStepState.BankCardBalanceEnter,
            is AccountsSetupOnboardingStepState.CashBalanceEnter -> {
                emptyFlow()
            }
        }
    }

    fun handleClickOnNegativeButtonIntent(
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        return when (state.stepState) {
            AccountsSetupOnboardingStepState.CashQuestion -> {
                val stepState = AccountsSetupOnboardingStepState.BankCardQuestion
                flowOf(AccountsSetupOnboardingEffect.StepChanged(stepState))
            }
            AccountsSetupOnboardingStepState.BankCardQuestion -> {
                flow {
                    onboardingCompletionExecutor.completeOnboarding(
                        cashBalance = state.cashBalance,
                        bankCardBalance = state.bankCardBalance
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
            is AccountsSetupOnboardingStepState.EverythingIsSetUp,
            is AccountsSetupOnboardingStepState.BankCardBalanceEnter,
            is AccountsSetupOnboardingStepState.CashBalanceEnter -> {
                emptyFlow()
            }
        }
    }

}