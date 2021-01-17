package serg.chuprin.finances.feature.onboarding.di.accountssetup

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStore
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.factory.AccountsSetupOnboardingStoreFactory

/**
 * Created by Sergey Chuprin on 09.12.2020.
 */
@Module
object AccountsSetupOnboardingModule {

    @[Provides ScreenScope]
    fun provideStore(factory: AccountsSetupOnboardingStoreFactory): AccountsSetupOnboardingStore {
        return factory.create()
    }

}