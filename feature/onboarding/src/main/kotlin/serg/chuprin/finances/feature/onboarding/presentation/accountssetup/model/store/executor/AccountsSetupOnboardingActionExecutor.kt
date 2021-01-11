package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingAction
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingEffect
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingEvent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingStepState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingActionExecutor @Inject constructor(
    private val questionStateIntentExecutor: AccountsSetupQuestionStateIntentExecutor,
    private val balanceEnterStepIntentExecutor: AccountsSetupBalanceEnterStepIntentExecutor
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
                        questionStateIntentExecutor.handleClickOnPositiveButtonIntent(state)
                    }
                    AccountsSetupOnboardingIntent.ClickOnNegativeButton -> {
                        questionStateIntentExecutor.handleClickOnNegativeButtonIntent(state)
                    }
                    is AccountsSetupOnboardingIntent.ClickOnAcceptBalanceButton -> {
                        balanceEnterStepIntentExecutor.handleClickOnAcceptBalanceButtonIntent(state)
                    }
                    is AccountsSetupOnboardingIntent.EnterBalance -> {
                        balanceEnterStepIntentExecutor.handleEnterBalanceIntent(intent)
                    }
                    AccountsSetupOnboardingIntent.ClickOnStartUsingAppButton -> {
                        handleClickOnStartUsingAppButtonIntent(state, eventConsumer)
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

    private fun handleClickOnStartUsingAppButtonIntent(
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

}