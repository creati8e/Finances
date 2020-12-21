package serg.chuprin.finances.feature.transactions.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.feature.transactions.presentation.model.builder.TransactionReportCellsBuilder
import serg.chuprin.finances.feature.transactions.presentation.model.builder.TransactionReportHeaderBuilder
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportActionExecutor @Inject constructor(
    private val cellsBuilder: TransactionReportCellsBuilder,
    private val headerBuilder: TransactionReportHeaderBuilder,
) : StoreActionExecutor<TransactionsReportAction, TransactionsReportState, TransactionsReportEffect, TransactionsReportEvent> {

    override fun invoke(
        action: TransactionsReportAction,
        state: TransactionsReportState,
        eventConsumer: Consumer<TransactionsReportEvent>,
        actionsFlow: Flow<TransactionsReportAction>
    ): Flow<TransactionsReportEffect> {
        return when (action) {
            is TransactionsReportAction.ExecuteIntent -> TODO()
            is TransactionsReportAction.FormatReport -> {
                handleFormatReportAction(action)
            }
        }
    }

    private fun handleFormatReportAction(
        action: TransactionsReportAction.FormatReport
    ): Flow<TransactionsReportEffect> {
        return flowOfSingleValue {
            val report = action.report
            val preparedData = report.preparedData
            TransactionsReportEffect.ReportBuilt(
                filter = report.filter,
                header = headerBuilder.build(report),
                listCells = cellsBuilder.build(preparedData.transactionsGroupedByDay)
            )
        }
    }

}