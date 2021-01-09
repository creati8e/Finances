package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.factory

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.*
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupOnboardingActionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 09.12.2020.
 */
@ScreenScope
class AccountsSetupOnboardingStoreFactory @Inject constructor(
    actionExecutor: AccountsSetupOnboardingActionExecutor,
    bootstrapper: AccountsSetupOnboardingStoreBootstrapper
) : AbsStoreFactory<AccountsSetupOnboardingIntent, AccountsSetupOnboardingEffect, AccountsSetupOnboardingAction, AccountsSetupOnboardingState, AccountsSetupOnboardingEvent, AccountsSetupOnboardingStore>(
    AccountsSetupOnboardingState(),
    AccountsSetupOnboardingStateReducer(),
    bootstrapper,
    actionExecutor,
    AccountsSetupOnboardingAction::ExecuteIntent
)