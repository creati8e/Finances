package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
class MoneyAccountCreationActionExecutor :
    StoreActionExecutor<MoneyAccountCreationAction, MoneyAccountCreationState, MoneyAccountCreationEffect, MoneyAccountCreationEvent> {

    override fun invoke(
        action: MoneyAccountCreationAction,
        state: MoneyAccountCreationState,
        eventConsumer: Consumer<MoneyAccountCreationEvent>,
        actionsFlow: Flow<MoneyAccountCreationAction>
    ): Flow<MoneyAccountCreationEffect> {
        TODO("Not yet implemented")
    }

}