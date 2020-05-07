package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsStore @Inject constructor(
    executor: MoneyAccountDetailsActionExecutor,
    bootstrapper: MoneyAccountDetailsStoreBootstrapper
) : BaseStateStore<MoneyAccountDetailsIntent, MoneyAccountDetailsEffect, MoneyAccountDetailsAction, MoneyAccountDetailsState, MoneyAccountDetailsEvent>(
    MoneyAccountDetailsState(),
    MoneyAccountDetailsStateReducer(),
    bootstrapper,
    executor,
    MoneyAccountDetailsIntentToActionMapper()
)