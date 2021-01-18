package serg.chuprin.finances.core.currency.choice.impl.di

import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository

/**
 * Created by Sergey Chuprin on 19.01.2021.
 */
interface CurrencyChoiceDependencies {

    val currencyRepository: CurrencyRepository

}