package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.factory

import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.*

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
class CurrencyChoiceOnboardingStoreParams(
    val store: serg.chuprin.finances.core.test.utils.TestStateStore<CurrencyChoiceOnboardingIntent, CurrencyChoiceOnboardingEffect, CurrencyChoiceOnboardingAction, CurrencyChoiceOnboardingState, CurrencyChoiceOnboardingEvent>,
    val userRepository: UserRepository,
    val currencyRepository: CurrencyRepository,
    val onboardingRepository: OnboardingRepository
)