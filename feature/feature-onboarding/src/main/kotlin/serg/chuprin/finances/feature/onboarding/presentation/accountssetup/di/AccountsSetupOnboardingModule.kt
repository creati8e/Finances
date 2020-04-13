package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.mvi.store.BaseStateStore
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.*

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