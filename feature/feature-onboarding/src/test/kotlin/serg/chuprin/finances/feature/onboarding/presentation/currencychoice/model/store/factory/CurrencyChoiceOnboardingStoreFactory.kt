package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.factory

import io.mockk.mockk
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.test.utils.TestStateStore
import serg.chuprin.finances.feature.onboarding.domain.usecase.CompleteCurrencyChoiceOnboardingUseCase
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.*

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
object CurrencyChoiceOnboardingStoreFactory {

    fun build(): CurrencyChoiceOnboardingStoreParams {
        val currencyRepository = mockk<CurrencyRepository>()
        val completeCurrencyChoiceOnboardingUseCase =
            mockk<CompleteCurrencyChoiceOnboardingUseCase>()

        val store = TestStateStore(
            CurrencyChoiceOnboardingState(),
            CurrencyChoiceOnboardingStateReducer(),
            CurrencyChoiceOnboardingStoreBootstrapper(currencyRepository),
            CurrencyChoiceOnboardingActionExecutor(
                SearchCurrenciesUseCase(currencyRepository),
                completeCurrencyChoiceOnboardingUseCase
            ),
            CurrencyChoiceOnboardingIntentToActionMapper()
        )
        return CurrencyChoiceOnboardingStoreParams(
            store,
            currencyRepository,
            completeCurrencyChoiceOnboardingUseCase
        )
    }

}