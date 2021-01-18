package serg.chuprin.finances.core.currency.choice.api.di

import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreFactoryApi

/**
 * Created by Sergey Chuprin on 19.01.2021.
 */
interface CurrencyChoiceStoreApi {

    val factory: CurrencyChoiceStoreFactoryApi

}