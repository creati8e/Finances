package serg.chuprin.finances.core.currency.choice.impl.domain.usecase

import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class SearchCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    suspend fun execute(searchQuery: String): List<Currency> {
        return currencyRepository.searchCurrencies(searchQuery)
    }

}