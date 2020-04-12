package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.api.presentation.model.mvi.reducer.StoreStateReducer
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.AccountsSetupOnboardingStepState

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
            is AccountsSetupOnboardingEffect.AccountBalanceEntered -> {
                state.copy(
                    cashBalance = effect.cashBalance,
                    bankCardBalance = effect.bankCardBalance
                )
            }
            is AccountsSetupOnboardingEffect.AmountEntered -> {
                when (val stepState = state.stepState) {
                    is AccountsSetupOnboardingStepState.CashAmountEnter -> {
                        state.copy(
                            stepState = stepState.copy(
                                enteredAmount = effect.formattedAmount,
                                acceptButtonIsVisible = effect.acceptButtonIsVisible
                            )
                        )
                    }
                    is AccountsSetupOnboardingStepState.BankCardAmountEnter -> {
                        state.copy(
                            stepState = stepState.copy(
                                enteredAmount = effect.formattedAmount,
                                acceptButtonIsVisible = effect.acceptButtonIsVisible
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
    }

}