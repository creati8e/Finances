package serg.chuprin.finances.feature.transactions.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.SpaceCell
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

    val cellsLiveData: LiveData<List<BaseCell>> = store.stateFlow
        .map { state ->
            buildList {
                addAll(state.header.dataPeriodAmountsChartListCell)
                if (state.header.dataPeriodAmountsChartListCell.isNotEmpty()) {
                    if (state.categorySharesCell != null) {
                        add(SpaceCell(sizeDp = 24))
                    }
                }
                state.categorySharesCell?.let(::add)
                addAll(state.transactionListCells)
            }
        }
        .distinctUntilChanged()
        .asLiveData()

    val headerLiveData: LiveData<TransactionReportHeader> =
        store.observeParticularStateAsLiveData(TransactionsReportState::header)

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}