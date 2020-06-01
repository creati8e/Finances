package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
class MoneyAccountCreationStore @Inject constructor(
    executor: MoneyAccountCreationActionExecutor,
    bootstrapper: MoneyAccountCreationBootstrapper
) : BaseStateStore<MoneyAccountCreationIntent, MoneyAccountCreationEffect, MoneyAccountCreationAction, MoneyAccountCreationState, MoneyAccountCreationEvent>(
    MoneyAccountCreationState(),
    MoneyAccountCreationStateReducer(),
    bootstrapper,
    executor,
    intentToActionMapper = MoneyAccountCreationAction::ExecuteIntent
)