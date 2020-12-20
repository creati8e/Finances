package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import serg.chuprin.finances.core.mvi.store.StateStore
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
typealias AccountsSetupOnboardingStore = StateStore<AccountsSetupOnboardingIntent, AccountsSetupOnboardingState, AccountsSetupOnboardingEvent>