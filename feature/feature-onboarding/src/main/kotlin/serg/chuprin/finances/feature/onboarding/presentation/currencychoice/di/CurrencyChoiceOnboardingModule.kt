package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.mvi.store.BaseStateStore
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.*

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
@Module
object CurrencyChoiceOnboardingModule {

    @[Provides ScreenScope]
    fun provideStore(
        executor: CurrencyChoiceOnboardingActionExecutor,
        bootstrapper: CurrencyChoiceOnboardingStoreBootstrapper
    ): CurrencyChoiceOnboardingStore {
        return BaseStateStore(
            executor = executor,
            bootstrapper = bootstrapper,
            initialState = CurrencyChoiceOnboardingState(),
            intentToActionMapper = CurrencyChoiceOnboardingIntentToActionMapper(),
            reducer = CurrencyChoiceOnboardingStateReducer()
        )
    }

}