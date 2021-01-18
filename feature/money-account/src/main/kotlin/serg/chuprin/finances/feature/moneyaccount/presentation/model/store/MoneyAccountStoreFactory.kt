package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 18.01.2021.
 */
@ScreenScope
class MoneyAccountStoreFactory @Inject constructor(
    actionExecutor: MoneyAccountActionExecutor,
    bootstrapper: MoneyAccountStoreBootstrapper,
    private val currencyChoiceStore: CurrencyChoiceStore
) : AbsStoreFactory<MoneyAccountIntent, MoneyAccountEffect, MoneyAccountAction, MoneyAccountState, MoneyAccountEvent, MoneyAccountStore>(
    MoneyAccountState(),
    MoneyAccountStateReducer(),
    bootstrapper,
    actionExecutor,
    intentToActionMapper = MoneyAccountAction::ExecuteIntent
) {

    override fun create(): MoneyAccountStore {
        return object :
            BaseStateStore<MoneyAccountIntent, MoneyAccountEffect, MoneyAccountAction, MoneyAccountState, MoneyAccountEvent>(
                reducer = reducer,
                actionExecutor = actionExecutor,
                initialState = initialState,
                bootstrapper = bootstrapper,
                reducerDispatcher = reducerDispatcher,
                intentToActionMapper = intentToActionMapper,
                backgroundDispatcher = backgroundDispatcher,
                bootstrapperDispatcher = bootstrapperDispatcher
            ), MoneyAccountStore {

            override fun start(intentsFlow: Flow<MoneyAccountIntent>, scope: CoroutineScope): Job {
                return scope.launch {
                    currencyChoiceStore.start(emptyFlow(), scope)
                    super.start(intentsFlow, scope)
                    currencyChoiceStore
                        .stateFlow
                        .collect {
                            ensureActive()
                            dispatchAction(MoneyAccountAction.UpdateCurrencyChoiceState(it))
                        }
                }
            }

            override fun dispatch(intent: CurrencyChoiceIntent) {
                currencyChoiceStore.dispatch(intent)
            }

        }
    }

}