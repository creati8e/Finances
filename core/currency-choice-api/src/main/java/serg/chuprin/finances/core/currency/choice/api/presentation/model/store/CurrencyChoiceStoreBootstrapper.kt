package serg.chuprin.finances.core.currency.choice.api.presentation.model.store

import kotlinx.coroutines.flow.Flow

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
interface CurrencyChoiceStoreBootstrapper {

    fun bootstrap(): Flow<CurrencyChoiceStoreInitialParams>

}