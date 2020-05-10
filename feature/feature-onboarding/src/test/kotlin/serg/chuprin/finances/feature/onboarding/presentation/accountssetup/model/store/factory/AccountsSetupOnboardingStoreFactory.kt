package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.factory

import io.mockk.mockk
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.test.utils.TestStateStore
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteAccountsSetupOnboardingUseCase
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingFinalStepBuilder
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingIntentToActionMapper
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStateReducer
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStoreBootstrapper
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupAmountEnterStepIntentExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupOnboardingActionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupOnboardingCompletionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupQuestionStateIntentExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
object AccountsSetupOnboardingStoreFactory {

    fun build(): AccountsSetupOnboardingStoreParams {

        val amountParser = mockk<AmountParser>()
        val userRepository = mockk<UserRepository>()
        val resourceManager = mockk<ResourceManger>()
        val amountFormatter = mockk<AmountFormatter>()

        val completeOnboardingUseCase = mockk<CompleteAccountsSetupOnboardingUseCase>()
        val onboardingCompletionExecutor = AccountsSetupOnboardingCompletionExecutor(
            resourceManger = resourceManager,
            completeOnboardingUseCase = completeOnboardingUseCase
        )
        val actionExecutor = AccountsSetupOnboardingActionExecutor(
            AccountsSetupQuestionStateIntentExecutor(
                onboardingCompletionExecutor = onboardingCompletionExecutor,
                finalStepBuilder = AccountsSetupOnboardingFinalStepBuilder(resourceManager)
            ),
            AccountsSetupAmountEnterStepIntentExecutor(
                amountParser = amountParser,
                amountFormatter = amountFormatter,
                onboardingCompletionExecutor = onboardingCompletionExecutor,
                finalStepBuilder = AccountsSetupOnboardingFinalStepBuilder(resourceManager)
            )
        )
        val store = TestStateStore(
            executor = actionExecutor,
            initialState = AccountsSetupOnboardingState(),
            reducer = AccountsSetupOnboardingStateReducer(),
            bootstrapper = AccountsSetupOnboardingStoreBootstrapper(userRepository),
            stateStoreIntentToActionMapper = AccountsSetupOnboardingIntentToActionMapper()
        )
        return AccountsSetupOnboardingStoreParams(
            store,
            amountParser,
            amountFormatter,
            userRepository,
            resourceManager,
            completeOnboardingUseCase
        )
    }

}