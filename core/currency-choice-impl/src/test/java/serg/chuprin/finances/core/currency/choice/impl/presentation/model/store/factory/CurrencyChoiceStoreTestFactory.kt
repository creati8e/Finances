package serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.factory

import io.mockk.mockk
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.currency.choice.impl.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceActionExecutor
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceStoreFactory
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceStoreTestBootstrapper
import serg.chuprin.finances.core.test.presentation.mvi.factory.TestStoreFactory.Companion.test

/**
 * Created by Sergey Chuprin on 09.12.2020.
 */
object CurrencyChoiceStoreTestFactory {

    fun build(): CurrencyChoiceStoreTestParams {
        val currencyRepository = mockk<CurrencyRepository>()

        val testStore =
            CurrencyChoiceStoreFactory(
                bootstrapper = CurrencyChoiceStoreTestBootstrapper(currencyRepository),
                actionExecutor = CurrencyChoiceActionExecutor(
                    SearchCurrenciesUseCase(currencyRepository)
                )
            ).test()

        return CurrencyChoiceStoreTestParams(
            testStore = testStore,
            currencyRepository = currencyRepository
        )
    }

}