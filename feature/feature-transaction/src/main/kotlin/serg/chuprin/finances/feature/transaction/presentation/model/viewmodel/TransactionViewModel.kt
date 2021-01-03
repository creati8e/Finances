package serg.chuprin.finances.feature.transaction.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionIntent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
@ScreenScope
class TransactionViewModel @Inject constructor(
    store: TransactionStore
) : BaseStoreViewModel<TransactionIntent>() {

    val chosenMoneyAccountLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData { state -> state.chosenMoneyAccount.formattedName }

    val chosenDateLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData { state -> state.chosenDate.formatted }

    val chosenCategoryLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData { state -> state.chosenCategory.formattedName }

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}