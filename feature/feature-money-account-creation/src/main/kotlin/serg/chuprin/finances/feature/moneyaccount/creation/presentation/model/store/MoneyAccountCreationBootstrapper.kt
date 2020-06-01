package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
class MoneyAccountCreationBootstrapper @Inject constructor() :
    StoreBootstrapper<MoneyAccountCreationAction> {

    override fun invoke(): Flow<MoneyAccountCreationAction> {
        TODO("Not yet implemented")
    }

}