package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntentToActionMapper
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStateReducer
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStore
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStoreBootstrapper
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupOnboardingActionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
@Module
object AccountsSetupOnboardingModule {

    @Provides
    @ScreenScope
    fun provideStore(
        executor: AccountsSetupOnboardingActionExecutor,
        bootstrapper: AccountsSetupOnboardingStoreBootstrapper
    ): AccountsSetupOnboardingStore {
        return BaseStateStore(
            executor = executor,
            bootstrapper = bootstrapper,
            initialState = AccountsSetupOnboardingState(),
            reducer = AccountsSetupOnboardingStateReducer(),
            intentToActionMapper = AccountsSetupOnboardingIntentToActionMapper()
        )
    }

}