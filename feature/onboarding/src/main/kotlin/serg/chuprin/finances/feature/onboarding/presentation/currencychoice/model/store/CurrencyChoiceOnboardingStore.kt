package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreIntentDispatcher
import serg.chuprin.finances.core.mvi.bootstrapper.BypassStoreBootstrapper
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject


/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
@ScreenScope
class CurrencyChoiceOnboardingStore @Inject constructor(
    actionExecutor: CurrencyChoiceOnboardingActionExecutor,
    private val currencyChoiceStore: CurrencyChoiceStore
) : BaseStateStore<CurrencyChoiceOnboardingIntent, CurrencyChoiceOnboardingEffect, CurrencyChoiceOnboardingAction, CurrencyChoiceOnboardingState, CurrencyChoiceOnboardingEvent>(
    CurrencyChoiceOnboardingState(),
    CurrencyChoiceOnboardingStateReducer(),
    BypassStoreBootstrapper(),
    actionExecutor,
    CurrencyChoiceOnboardingAction::ExecuteIntent
), CurrencyChoiceStoreIntentDispatcher by currencyChoiceStore {

    override fun start(
        intentsFlow: Flow<CurrencyChoiceOnboardingIntent>,
        scope: CoroutineScope
    ): Job {
        return scope.launch {
            currencyChoiceStore.start(emptyFlow(), scope)
            super.start(intentsFlow, scope)
            currencyChoiceStore
                .stateFlow
                .collect {
                    ensureActive()
                    dispatchAction(CurrencyChoiceOnboardingAction.UpdateCurrencyChoiceState(it))
                }
        }
    }

}