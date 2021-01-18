package serg.chuprin.finances.core.impl.presentation.feature.factory

import io.mockk.mockk
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.impl.presentation.feature.CurrencyChoiceStoreTestBootstrapper
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceActionExecutor
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceStoreFactory

/**
 * Created by Sergey Chuprin on 09.12.2020.
 */
object CurrencyChoiceStoreTestFactory {

    fun build(): CurrencyChoiceStoreTestParams {
        val currencyRepository = mockk<CurrencyRepository>()

        val testStore = serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceStoreFactory(
            bootstrapper = CurrencyChoiceStoreTestBootstrapper(currencyRepository),
            actionExecutor = serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceActionExecutor(
                SearchCurrenciesUseCase(currencyRepository)
            )
        ).test()

        return CurrencyChoiceStoreTestParams(
            testStore = testStore,
            currencyRepository = currencyRepository
        )
    }

}