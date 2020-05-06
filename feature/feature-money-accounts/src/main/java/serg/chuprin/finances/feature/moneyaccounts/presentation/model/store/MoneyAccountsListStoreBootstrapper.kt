package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
class MoneyAccountsListStoreBootstrapper @Inject constructor() :
    StoreBootstrapper<MoneyAccountsListAction> {

    override fun invoke(): Flow<MoneyAccountsListAction> {
        TODO("Not yet implemented")
    }

}