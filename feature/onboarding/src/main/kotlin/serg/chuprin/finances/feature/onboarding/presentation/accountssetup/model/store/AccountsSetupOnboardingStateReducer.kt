package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingStepState

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingStateReducer :
    StoreStateReducer<AccountsSetupOnboardingEffect, AccountsSetupOnboardingState> {

    override fun invoke(
        effect: AccountsSetupOnboardingEffect,
        state: AccountsSetupOnboardingState
    ): AccountsSetupOnboardingState {
        return when (effect) {
            is AccountsSetupOnboardingEffect.StepChanged -> {
                state.copy(stepState = effect.stepState)
            }
            is AccountsSetupOnboardingEffect.AccountBalanceAccepted -> {
                state.copy(
                    cashBalance = effect.cashBalance,
                    bankCardBalance = effect.bankCardBalance
                )
            }
            is AccountsSetupOnboardingEffect.CurrencyIsSet -> {
                state.copy(currency = effect.currency)
            }
            is AccountsSetupOnboardingEffect.BalanceEntered -> {
                reduceBalanceEnteredEffect(state, effect)
            }
        }
    }

    private fun reduceBalanceEnteredEffect(
        state: AccountsSetupOnboardingState,
        effect: AccountsSetupOnboardingEffect.BalanceEntered
    ): AccountsSetupOnboardingState {
        return when (val stepState = state.stepState) {
            is AccountsSetupOnboardingStepState.CashBalanceEnter -> {
                state.copy(
                    stepState = stepState.copy(
                        balance = effect.balance,
                        acceptBalanceButtonIsEnabled = effect.acceptButtonIsEnabled
                    )
                )
            }
            is AccountsSetupOnboardingStepState.BankCardBalanceEnter -> {
                state.copy(
                    stepState = stepState.copy(
                        balance = effect.balance,
                        acceptBalanceButtonIsEnabled = effect.acceptButtonIsEnabled
                    )
                )
            }
            AccountsSetupOnboardingStepState.BankCardQuestion,
            AccountsSetupOnboardingStepState.CashQuestion,
            is AccountsSetupOnboardingStepState.EverythingIsSetUp -> {
                // Unreachable branch.
                state
            }
        }
    }

}