package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsActionExecutor @Inject constructor() :
    StoreActionExecutor<MoneyAccountDetailsAction, MoneyAccountDetailsState, MoneyAccountDetailsEffect, MoneyAccountDetailsEvent> {

    override fun invoke(
        action: MoneyAccountDetailsAction,
        state: MoneyAccountDetailsState,
        eventConsumer: Consumer<MoneyAccountDetailsEvent>,
        actionsFlow: Flow<MoneyAccountDetailsAction>
    ): Flow<MoneyAccountDetailsEffect> {
        TODO("Not yet implemented")
    }

}