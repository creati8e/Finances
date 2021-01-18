package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionToCategory
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
            cells = buildCells(widget.transactionToCategory, widget.moneyAccounts),
            showMoreTransactionsButtonIsVisible = widget.transactionToCategory.isNotEmpty()
        )
    }

    private fun buildCells(
        transactionToCategory: TransactionToCategory,
        moneyAccounts: Map<Id, MoneyAccount>
    ): List<BaseCell> {
        if (transactionToCategory.isEmpty()) {
            return listOf(DashboardRecentTransactionsZeroDataCell())
        }
        return transactionToCategory.mapNotNull { (transaction, categoryWithParent) ->
            val moneyAccount = moneyAccounts[transaction.moneyAccountId] ?: return@mapNotNull null
            transactionCellBuilder.build(
                transaction = transaction,
                moneyAccount = moneyAccount,
                categoryWithParent = categoryWithParent,
                dateTimeFormattingMode = TransactionCellBuilder.DateTimeFormattingMode.DATE_AND_TIME
            )
        }
    }

}