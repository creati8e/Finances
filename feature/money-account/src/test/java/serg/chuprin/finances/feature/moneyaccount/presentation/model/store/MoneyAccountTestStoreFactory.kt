package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreIntentDispatcher
import serg.chuprin.finances.core.test.presentation.mvi.store.TestStoreImpl

/**
 * Created by Sergey Chuprin on 19.01.2021.
 */
class MoneyAccountTestStoreFactory(
    private val currencyChoiceStore: CurrencyChoiceTestStore,
    private val actionExecutor: MoneyAccountActionExecutor,
    private val bootstrapper: MoneyAccountStoreBootstrapper
) {

    fun create(): MoneyAccountTestStore {
        return object : CurrencyChoiceStoreIntentDispatcher,
            TestStoreImpl<MoneyAccountIntent, MoneyAccountEffect, MoneyAccountAction, MoneyAccountState, MoneyAccountEvent>(
                actionExecutor = actionExecutor,
                bootstrapper = bootstrapper,
                initialState = MoneyAccountState(),
                reducer = MoneyAccountStateReducer(),
                intentToActionMapper = MoneyAccountAction::ExecuteIntent
            ), MoneyAccountTestStore {

            override fun start(intentsFlow: Flow<MoneyAccountIntent>, scope: CoroutineScope): Job {
                return scope.launch {
                    currencyChoiceStore.start()
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