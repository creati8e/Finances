package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.viewmodel

import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsIntent
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsViewModel @Inject constructor(
    store: MoneyAccountDetailsStore
) : BaseStoreViewModel<MoneyAccountDetailsIntent>()