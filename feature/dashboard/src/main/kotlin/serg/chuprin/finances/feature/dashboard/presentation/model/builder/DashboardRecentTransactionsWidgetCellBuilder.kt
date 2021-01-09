package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionCategories
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
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
            cells = buildCells(widget.transactionCategories, widget.moneyAccounts),
            showMoreTransactionsButtonIsVisible = widget.transactionCategories.isNotEmpty()
        )
    }

    private fun buildCells(
        transactionCategories: TransactionCategories,
        moneyAccounts: Map<Id, MoneyAccount>
    ): List<BaseCell> {
        if (transactionCategories.isEmpty()) {
            return listOf(DashboardRecentTransactionsZeroDataCell())
        }
        return transactionCategories.map { (transaction, categoryWithParent) ->
            transactionCellBuilder.build(
                transaction = transaction,
                categoryWithParent = categoryWithParent,
                moneyAccount = moneyAccounts.getValue(transaction.moneyAccountId),
                dateTimeFormattingMode = TransactionCellBuilder.DateTimeFormattingMode.DATE_AND_TIME
            )
        }
    }

}