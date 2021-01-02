package serg.chuprin.finances.feature.transaction.presentation.model.viewmodel

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

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}