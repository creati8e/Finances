package serg.chuprin.finances.core.impl.presentation.feature.factory

import io.mockk.mockk
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.impl.presentation.feature.CurrencyChoiceStoreTestBootstrapper
import serg.chuprin.finances.core.impl.presentation.model.store.currencychoice.CurrencyChoiceActionExecutor
import serg.chuprin.finances.core.impl.presentation.model.store.currencychoice.CurrencyChoiceStoreFactory
import serg.chuprin.finances.core.test.factory.TestStoreFactory.Companion.test

/**
 * Created by Sergey Chuprin on 09.12.2020.
 */
object CurrencyChoiceStoreTestFactory {

    fun build(): CurrencyChoiceStoreTestParams {
        val currencyRepository = mockk<CurrencyRepository>()

        val testStore = CurrencyChoiceStoreFactory(
            bootstrapper = CurrencyChoiceStoreTestBootstrapper(currencyRepository),
            actionExecutor = CurrencyChoiceActionExecutor(SearchCurrenciesUseCase(currencyRepository))
        ).test()

        return CurrencyChoiceStoreTestParams(
            testStore = testStore,
            currencyRepository = currencyRepository
        )
    }

}