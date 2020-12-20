package serg.chuprin.finances.feature.transactions.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.TransactionsGroupedByDay
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.DateDividerCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.feature.transactions.R
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.12.2020.
 */
class TransactionReportCellsBuilder @Inject constructor(
    private val dateTimeFormatter: DateTimeFormatter,
    private val transactionCellBuilder: TransactionCellBuilder
) {

    fun build(
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