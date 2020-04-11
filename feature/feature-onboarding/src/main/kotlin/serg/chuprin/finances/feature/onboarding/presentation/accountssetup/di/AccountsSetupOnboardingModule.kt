package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.mvi.bootstrapper.BypassStoreBootstrapper
import serg.chuprin.finances.core.api.presentation.model.mvi.mapper.BypassStateStoreIntentToActionMapper
import serg.chuprin.finances.core.api.presentation.model.mvi.store.BaseStateStore
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingActionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStateReducer
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStore

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
@Module
object AccountsSetupOnboardingModule {

    @Provides
    @ScreenScope
    fun provideStore(
        executor: AccountsSetupOnboardingActionExecutor
    ): AccountsSetupOnboardingStore {
        return BaseStateStore(
            executor = executor,
            bootstrapper = BypassStoreBootstrapper(),
            initialState = AccountsSetupOnboardingState(),
            reducer = AccountsSetupOnboardingStateReducer(),
            intentToActionMapper = BypassStateStoreIntentToActionMapper()
        )
    }

}