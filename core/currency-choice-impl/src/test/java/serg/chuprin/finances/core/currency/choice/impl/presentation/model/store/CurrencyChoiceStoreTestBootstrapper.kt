package serg.chuprin.finances.core.currency.choice.impl.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper

/**
 * Created by Sergey Chuprin on 09.12.2020.
 */
class CurrencyChoiceStoreTestBootstrapper(
    private val currencyRepository: CurrencyRepository,
) : StoreBootstrapper<CurrencyChoiceAction> {

    override fun invoke(): Flow<CurrencyChoiceAction> {
        return flowOfSingleValue {
            CurrencyChoiceAction.SetCurrenciesParams(
                chosenCurrency = currencyRepository.getDefaultCurrency(),
                availableCurrencies = currencyRepository.getAvailableCurrencies()
            )
        }
    }

}