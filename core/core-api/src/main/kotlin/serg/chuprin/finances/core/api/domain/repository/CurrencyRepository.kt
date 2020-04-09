package serg.chuprin.finances.core.api.domain.repository

import java.util.*

/**
 * Created by Sergey Chuprin on 07.04.2020.
 */
interface CurrencyRepository {

    suspend fun getDefaultCurrency(): Currency

    suspend fun getAvailableCurrencies(): Set<Currency>

}