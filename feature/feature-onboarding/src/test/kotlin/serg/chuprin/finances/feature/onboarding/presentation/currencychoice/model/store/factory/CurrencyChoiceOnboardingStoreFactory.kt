package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.factory

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
// FIXME: 09.12.2020  
//object CurrencyChoiceOnboardingStoreFactory {
//
//    fun build(): CurrencyChoiceOnboardingStoreParams {
//        val currencyRepository = mockk<CurrencyRepository>()
//        val completeCurrencyChoiceOnboardingUseCase =
//            mockk<CompleteCurrencyChoiceOnboardingUseCase>()
//
//        val store = TestStateStore(
//            CurrencyChoiceOnboardingState(),
//            CurrencyChoiceOnboardingStateReducer(),
//            CurrencyChoiceOnboardingStoreBootstrapper(currencyRepository),
//            CurrencyChoiceOnboardingActionExecutor(
//                SearchCurrenciesUseCase(currencyRepository),
//                completeCurrencyChoiceOnboardingUseCase
//            ),
//            CurrencyChoiceOnboardingIntentToActionMapper()
//        )
//        return CurrencyChoiceOnboardingStoreParams(
//            store,
//            currencyRepository,
//            completeCurrencyChoiceOnboardingUseCase
//        )
//    }
//
//}