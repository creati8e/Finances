package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
typealias CurrencyChoiceOnboardingStore = StateStore<CurrencyChoiceOnboardingIntent, CurrencyChoiceOnboardingState, CurrencyChoiceOnboardingEvent>