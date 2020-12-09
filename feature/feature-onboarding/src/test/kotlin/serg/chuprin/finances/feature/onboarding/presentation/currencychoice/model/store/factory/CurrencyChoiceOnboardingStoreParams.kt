package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.factory

import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.test.store.TestStore
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteCurrencyChoiceOnboardingUseCase
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.CurrencyChoiceOnboardingEvent
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.CurrencyChoiceOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.CurrencyChoiceOnboardingState

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
class CurrencyChoiceOnboardingStoreParams(
    val store: TestStore<CurrencyChoiceOnboardingIntent, CurrencyChoiceOnboardingState, CurrencyChoiceOnboardingEvent>,
    val currencyRepository: CurrencyRepository,
    val completeCurrencyChoiceOnboardingUseCase: CompleteCurrencyChoiceOnboardingUseCase
)