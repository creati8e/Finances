package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreInitialParams
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
class CurrencyChoiceStoreBootstrapperImpl @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : CurrencyChoiceStoreBootstrapper {

    override fun bootstrap(): Flow<CurrencyChoiceStoreInitialParams> {
        return flowOfSingleValue {
            val defaultCurrency = currencyRepository.getDefaultCurrency()
            val availableCurrencies = currencyRepository.getAvailableCurrencies()
            CurrencyChoiceStoreInitialParams(
                currentCurrency = defaultCurrency,
                availableCurrencies = availableCurrencies
            )
        }
    }

}