package serg.chuprin.finances.feature.transactions.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.TransactionsGroupedByDay
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.DateDividerCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.feature.transactions.R
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportActionExecutor @Inject constructor(
    private val dateTimeFormatter: DateTimeFormatter,
    private val transactionCellBuilder: TransactionCellBuilder
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
            TransactionsReportEffect.ReportBuilt(
                filter = action.report.filter,
                cells = buildCells(action.report.transactionsGroupedByDay)
            )
        }
    }

    private fun buildCells(
        transactionsGroupedByDay: TransactionsGroupedByDay
    ): List<BaseCell> {
        if (transactionsGroupedByDay.isEmpty()) {
            return listOf(
                ZeroDataCell(
                    iconRes = null,
                    contentMessageRes = null,
                    titleRes = R.string.transactions_report_zero_data_title
                )
            )
        }
        return transactionsGroupedByDay.entries
            .fold(mutableListOf()) { cells, (localDate, transactionsWithCategories) ->
                cells.apply {
                    add(
                        DateDividerCell(
                            localDate = localDate,
                            dateFormatted = dateTimeFormatter.formatAsDay(localDate)
                        )
                    )
                    transactionsWithCategories.forEach { (transaction, category) ->
                        add(transactionCellBuilder.build(transaction, category))
                    }
                }
            }
    }

}