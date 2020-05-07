package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsStoreBootstrapper @Inject constructor() :
    StoreBootstrapper<MoneyAccountDetailsAction> {

    override fun invoke(): Flow<MoneyAccountDetailsAction> {
        TODO("Not yet implemented")
    }

}