package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class CurrencyChoiceStoreBootstrapper @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : StoreBootstrapper<CurrencyChoiceAction> {

    override fun invoke(): Flow<CurrencyChoiceAction> {
        return flowOfSingleValue {
            val defaultCurrency = currencyRepository.getDefaultCurrency()
            val availableCurrencies = currencyRepository.getAvailableCurrencies()
            CurrencyChoiceAction.SetCurrenciesParams(
                currentCurrency = defaultCurrency,
                availableCurrencies = availableCurrencies
            )
        }
    }

}