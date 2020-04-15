package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.factory

import io.mockk.mockk
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.test.utils.TestStateStore
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteCurrencyChoiceOnboardingUseCase
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.*

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
object CurrencyChoiceOnboardingStoreFactory {

    fun build(): CurrencyChoiceOnboardingStoreParams {
        val userRepository = mockk<UserRepository>()
        val currencyRepository = mockk<CurrencyRepository>()
        val onboardingRepository = mockk<OnboardingRepository>()

        val store = TestStateStore(
            CurrencyChoiceOnboardingState(),
            CurrencyChoiceOnboardingStateReducer(),
            CurrencyChoiceOnboardingStoreBootstrapper(currencyRepository),
            CurrencyChoiceOnboardingActionExecutor(
                SearchCurrenciesUseCase(currencyRepository),
                CompleteCurrencyChoiceOnboardingUseCase(userRepository, onboardingRepository)
            ),
            CurrencyChoiceOnboardingIntentToActionMapper()
        )
        return CurrencyChoiceOnboardingStoreParams(
            store,
            userRepository,
            currencyRepository,
            onboardingRepository
        )
    }

}