package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.factory

import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.test.utils.TestStateStore
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteAccountsSetupOnboardingUseCase
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingAction
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingEffect
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingEvent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class AccountsSetupOnboardingStoreParams(
    val store: TestStateStore<AccountsSetupOnboardingIntent, AccountsSetupOnboardingEffect, AccountsSetupOnboardingAction, AccountsSetupOnboardingState, AccountsSetupOnboardingEvent>,
    val amountParser: AmountParser,
    val amountFormatter: AmountFormatter,
    val userRepository: UserRepository,
    val resourceManager: ResourceManger,
    val completeOnboardingUseCase: CompleteAccountsSetupOnboardingUseCase
)