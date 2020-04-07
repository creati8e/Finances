package serg.chuprin.finances.feature.onboarding.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
typealias OnboardingStore = StateStore<OnboardingIntent, OnboardingState, OnboardingEvent>