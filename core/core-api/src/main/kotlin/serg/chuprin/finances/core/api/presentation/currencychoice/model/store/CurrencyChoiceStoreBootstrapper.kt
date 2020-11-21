package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

import kotlinx.coroutines.flow.Flow

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
interface CurrencyChoiceStoreBootstrapper {

    fun bootstrap(): Flow<CurrencyChoiceStoreInitialParams>

}