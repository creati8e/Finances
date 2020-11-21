package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.viewmodel

import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationIntent
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store.MoneyAccountCreationStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
class MoneyAccountCreationViewModel @Inject constructor(
    store: MoneyAccountCreationStore
) : BaseStoreViewModel<MoneyAccountCreationIntent>() {

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}