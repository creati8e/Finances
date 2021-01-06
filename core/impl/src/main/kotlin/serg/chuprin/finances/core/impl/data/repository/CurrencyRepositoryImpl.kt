package serg.chuprin.finances.core.impl.data.repository

import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.04.2020.
 */
internal class CurrencyRepositoryImpl @Inject constructor() : CurrencyRepository {

    override suspend fun getDefaultCurrency(): Currency {
        return Currency.getInstance(Locale.getDefault())
    }

    override suspend fun getAvailableCurrencies(): List<Currency> {
        return Currency.getAvailableCurrencies().toList()
    }

    override suspend fun searchCurrencies(searchQuery: String): List<Currency> {
        return getAvailableCurrencies()
            .filter { currency ->
                currency.displayName.contains(searchQuery, ignoreCase = true)
                        || currency.currencyCode.contains(searchQuery, ignoreCase = true)
            }
    }

}