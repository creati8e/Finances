package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import androidx.core.util.Consumer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.presentation.model.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingActionExecutor @Inject constructor() :
    StoreActionExecutor<AccountsSetupOnboardingIntent, AccountsSetupOnboardingState, AccountsSetupOnboardingEffect, AccountsSetupOnboardingEvent> {

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
        }
    }

    private fun handleClickOnPositiveButtonIntent(
        state: AccountsSetupOnboardingState
    ): Flow<AccountsSetupOnboardingEffect> {
        // TODO: Implement for other states.
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
        // TODO: Implement for other states.
        return when (state.stepState) {
            AccountsSetupOnboardingStepState.CashQuestion -> {
                val stepState = AccountsSetupOnboardingStepState.BankCardQuestion
                flowOf(AccountsSetupOnboardingEffect.StepChanged(stepState))
            }
            AccountsSetupOnboardingStepState.BankCardQuestion -> {
                // TODO: Implement logic if not a single bank account was set up.
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

}