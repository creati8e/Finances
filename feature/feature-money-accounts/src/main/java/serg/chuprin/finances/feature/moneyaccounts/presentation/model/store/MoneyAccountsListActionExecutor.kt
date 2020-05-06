package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
class MoneyAccountsListActionExecutor @Inject constructor() :
    StoreActionExecutor<MoneyAccountsListAction, MoneyAccountsListState, MoneyAccountsListEffect, MoneyAccountsListEvent> {

    override fun invoke(
        action: MoneyAccountsListAction,
        state: MoneyAccountsListState,
        eventConsumer: Consumer<MoneyAccountsListEvent>,
        actionsFlow: Flow<MoneyAccountsListAction>
    ): Flow<MoneyAccountsListEffect> {
        TODO("Not yet implemented")
    }

}