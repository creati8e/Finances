package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.factory

import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.test.utils.TestStateStore
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteCurrencyChoiceOnboardingUseCase
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.*

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
class CurrencyChoiceOnboardingStoreParams(
    val store: TestStateStore<CurrencyChoiceOnboardingIntent, CurrencyChoiceOnboardingEffect, CurrencyChoiceOnboardingAction, CurrencyChoiceOnboardingState, CurrencyChoiceOnboardingEvent>,
    val currencyRepository: CurrencyRepository,
    val completeCurrencyChoiceOnboardingUseCase: CompleteCurrencyChoiceOnboardingUseCase
)