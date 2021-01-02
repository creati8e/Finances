package serg.chuprin.finances.feature.transaction.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionStoreBootstrapper @Inject constructor() : StoreBootstrapper<TransactionAction> {

    override fun invoke(): Flow<TransactionAction> {
        TODO("Not yet implemented")
    }

}