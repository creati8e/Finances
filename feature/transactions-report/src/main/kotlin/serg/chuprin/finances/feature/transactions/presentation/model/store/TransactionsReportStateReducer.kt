package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportStateReducer :
    StoreStateReducer<TransactionsReportEffect, TransactionsReportState> {

    override fun invoke(
        what: TransactionsReportEffect,
        state: TransactionsReportState
    ): TransactionsReportState {
        return when (what) {
            is TransactionsReportEffect.ReportBuilt -> {
                state.copy(
                    header = what.header,
                    filter = what.filter,
                    listCells = what.listCells
                )
            }
        }
    }

}