package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreInitialParams
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
class CurrencyChoiceStoreBootstrapperImpl @Inject constructor() : CurrencyChoiceStoreBootstrapper {

    override fun bootstrap(): Flow<CurrencyChoiceStoreInitialParams> {
        TODO("Not yet implemented")
    }

}