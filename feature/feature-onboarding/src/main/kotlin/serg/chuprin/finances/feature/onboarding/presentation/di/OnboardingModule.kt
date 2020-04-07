package serg.chuprin.finances.feature.onboarding.presentation.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.mvi.store.BaseStateStore
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingIntentToActionMapper
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingStore
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingStoreBootstrapper
import serg.chuprin.finances.feature.onboarding.presentation.model.store.executor.OnboardingActionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.model.store.reducer.OnboardingCurrencyChoiceStepStateReducer
import serg.chuprin.finances.feature.onboarding.presentation.model.store.reducer.OnboardingStateReducer

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
@Module
object OnboardingModule {

    @[Provides ScreenScope]
    fun provideStore(
        executor: OnboardingActionExecutor,
        bootstrapper: OnboardingStoreBootstrapper
    ): OnboardingStore {
        return BaseStateStore(
            executor = executor,
            bootstrapper = bootstrapper,
            initialState = OnboardingState(),
            intentToActionMapper = OnboardingIntentToActionMapper(),
            reducer = OnboardingStateReducer(OnboardingCurrencyChoiceStepStateReducer())
        )
    }

}