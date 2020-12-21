package serg.chuprin.finances.feature.transactions.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.transactions.presentation.model.TransactionReportHeader
import serg.chuprin.finances.feature.transactions.presentation.model.store.TransactionsReportIntent
import serg.chuprin.finances.feature.transactions.presentation.model.store.TransactionsReportState
import serg.chuprin.finances.feature.transactions.presentation.model.store.TransactionsReportStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportViewModel @Inject constructor(
    store: TransactionsReportStore
) : BaseStoreViewModel<TransactionsReportIntent>() {

    val transactionsListCellsLiveData: LiveData<List<BaseCell>> =
        store.observeParticularStateAsLiveData(TransactionsReportState::transactionListCells)

    val headerLiveData: LiveData<TransactionReportHeader> =
        store.observeParticularStateAsLiveData(TransactionsReportState::header)

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}