package serg.chuprin.finances.feature.transactions.presentation.model.viewmodel

import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.transactions.presentation.model.store.TransactionsReportIntent
import serg.chuprin.finances.feature.transactions.presentation.model.store.TransactionsReportStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportViewModel @Inject constructor(
    store: TransactionsReportStore
) : BaseStoreViewModel<TransactionsReportIntent>() {

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}