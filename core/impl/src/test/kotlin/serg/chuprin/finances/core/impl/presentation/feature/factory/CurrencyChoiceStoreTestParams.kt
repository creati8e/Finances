package serg.chuprin.finances.core.impl.presentation.feature.factory

import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.test.store.TestStore

/**
 * Created by Sergey Chuprin on 09.12.2020.
 */
class CurrencyChoiceStoreTestParams(
    val testStore: TestStore<CurrencyChoiceIntent, CurrencyChoiceState, Nothing>,
    val currencyRepository: CurrencyRepository
) {

    operator fun component1() = testStore
    operator fun component2() = currencyRepository

}