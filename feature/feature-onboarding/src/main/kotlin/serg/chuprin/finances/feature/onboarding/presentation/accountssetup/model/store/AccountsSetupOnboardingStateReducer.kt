package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.api.presentation.model.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class AccountsSetupOnboardingStateReducer :
    StoreStateReducer<AccountsSetupOnboardingEffect, AccountsSetupOnboardingState> {

    override fun invoke(
        effect: AccountsSetupOnboardingEffect,
        state: AccountsSetupOnboardingState
    ): AccountsSetupOnboardingState {
        TODO("Not yet implemented")
    }

}