package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
@ScreenScope
class CurrencyChoiceOnboardingStore @Inject constructor(
    executor: CurrencyChoiceOnboardingActionExecutor,
    bootstrapper: CurrencyChoiceOnboardingStoreBootstrapper
) : BaseStateStore<CurrencyChoiceOnboardingIntent, CurrencyChoiceOnboardingEffect, CurrencyChoiceOnboardingAction, CurrencyChoiceOnboardingState, CurrencyChoiceOnboardingEvent>(
    CurrencyChoiceOnboardingState(),
    CurrencyChoiceOnboardingStateReducer(),
    bootstrapper,
    executor,
    CurrencyChoiceOnboardingIntentToActionMapper()
)