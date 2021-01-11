package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.factory

import io.mockk.mockk
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.test.presentation.mvi.factory.TestStoreFactory.Companion.test
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteAccountsSetupOnboardingUseCase
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingFinalStepBuilder
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.AccountsSetupOnboardingStoreBootstrapper
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupBalanceEnterStepIntentExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupOnboardingActionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupOnboardingCompletionExecutor
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.executor.AccountsSetupQuestionStateIntentExecutor

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
object AccountsSetupOnboardingStoreTestFactory {

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
            AccountsSetupBalanceEnterStepIntentExecutor(
                amountParser = amountParser,
                finalStepBuilder = AccountsSetupOnboardingFinalStepBuilder(resourceManager),
                onboardingCompletionExecutor = onboardingCompletionExecutor
            )
        )

        val testStore = AccountsSetupOnboardingStoreFactory(
            actionExecutor = actionExecutor,
            bootstrapper = AccountsSetupOnboardingStoreBootstrapper(userRepository)
        ).test()

        return AccountsSetupOnboardingStoreParams(
            testStore,
            amountParser,
            amountFormatter,
            userRepository,
            resourceManager,
            completeOnboardingUseCase
        )
    }

}