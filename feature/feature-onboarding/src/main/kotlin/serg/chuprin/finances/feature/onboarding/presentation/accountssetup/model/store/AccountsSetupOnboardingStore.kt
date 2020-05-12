package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupOnboardingActionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
@ScreenScope
class AccountsSetupOnboardingStore @Inject constructor(
    executor: AccountsSetupOnboardingActionExecutor,
    bootstrapper: AccountsSetupOnboardingStoreBootstrapper
) : BaseStateStore<AccountsSetupOnboardingIntent, AccountsSetupOnboardingEffect, AccountsSetupOnboardingAction, AccountsSetupOnboardingState, AccountsSetupOnboardingEvent>(
    AccountsSetupOnboardingState(),
    AccountsSetupOnboardingStateReducer(),
    bootstrapper,
    executor,
    AccountsSetupOnboardingIntentToActionMapper()
)