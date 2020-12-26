package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions.DashboardRecentTransactionsZeroDataCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardRecentTransactionsWidgetCellBuilder @Inject constructor(
    private val transactionCellBuilder: TransactionCellBuilder
) : DashboardWidgetCellBuilder {

    override fun build(widget: DashboardWidget): DashboardWidgetCell? {
        if (widget !is DashboardWidget.RecentTransactions) {
            return null
        }
        return DashboardWidgetCell.RecentTransactions(
            widget = widget,
            cells = buildCells(widget.transactionWithCategoryMap),
            showMoreTransactionsButtonIsVisible = widget.transactionWithCategoryMap.isNotEmpty()
        )
    }

    private fun buildCells(
        transactionWithCategoryMap: Map<Transaction, TransactionCategoryWithParent?>
    ): List<BaseCell> {
        if (transactionWithCategoryMap.isEmpty()) {
            return listOf(DashboardRecentTransactionsZeroDataCell())
        }
        return transactionWithCategoryMap.map { (transaction, categoryWithParent) ->
            transactionCellBuilder.build(
                transaction = transaction,
                categoryWithParent = categoryWithParent,
                dateTimeFormattingMode = TransactionCellBuilder.DateTimeFormattingMode.DATE_AND_TIME
            )
        }
    }

}