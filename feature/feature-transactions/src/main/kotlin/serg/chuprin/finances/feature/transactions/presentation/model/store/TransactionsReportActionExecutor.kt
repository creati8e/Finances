package serg.chuprin.finances.feature.transactions.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportActionExecutor @Inject constructor() :
    StoreActionExecutor<TransactionsReportAction, TransactionsReportState, TransactionsReportEffect, TransactionsReportEvent> {

    override fun invoke(
        action: TransactionsReportAction,
        state: TransactionsReportState,
        eventConsumer: Consumer<TransactionsReportEvent>,
        actionsFlow: Flow<TransactionsReportAction>
    ): Flow<TransactionsReportEffect> {
        TODO("Not yet implemented")
    }

}