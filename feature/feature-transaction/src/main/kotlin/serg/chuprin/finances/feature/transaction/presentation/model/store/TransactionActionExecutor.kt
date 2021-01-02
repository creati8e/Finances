package serg.chuprin.finances.feature.transaction.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionActionExecutor @Inject constructor() :
    StoreActionExecutor<TransactionAction, TransactionState, TransactionEffect, TransactionEvent> {

    override fun invoke(
        action: TransactionAction,
        state: TransactionState,
        eventConsumer: Consumer<TransactionEvent>,
        actionsFlow: Flow<TransactionAction>
    ): Flow<TransactionEffect> {
        TODO("Not yet implemented")
    }

}