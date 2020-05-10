package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.DashboardWidget
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions.DashboardRecentTransactionsZeroDataCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions.DashboardTransactionCell
import java.util.*
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardRecentTransactionsWidgetCellBuilder @Inject constructor(
    private val resourceManger: ResourceManger,
    private val amountFormatter: AmountFormatter,
    private val dateTimeFormatter: DateTimeFormatter,
    private val categoryColorFormatter: CategoryColorFormatter
) : DashboardWidgetCellBuilder {

    override fun build(widget: DashboardWidget): DashboardWidgetCell? {
        if (widget !is DashboardWidget.RecentTransactions) {
            return null
        }
        return DashboardWidgetCell.RecentTransactions(
            widget = widget,
            cells = buildCells(widget.currency, widget.transactionWithCategoryMap),
            showMoreTransactionsButtonIsVisible = widget.transactionWithCategoryMap.isNotEmpty()
        )
    }

    private fun buildCells(
        currency: Currency,
        transactionWithCategoryMap: Map<Transaction, TransactionCategoryWithParent?>
    ): List<BaseCell> {
        if (transactionWithCategoryMap.isEmpty()) {
            return listOf(DashboardRecentTransactionsZeroDataCell())
        }
        return transactionWithCategoryMap.map { (transaction, categoryWithParent) ->
            val formattedAmount = amountFormatter.format(
                round = true,
                withSign = true,
                currency = currency,
                withCurrencySymbol = true,
                amount = transaction.amount
            )
            val (parentCategoryName, subcategoryName) = categoryWithParent.format()
            DashboardTransactionCell(
                amount = formattedAmount,
                transaction = transaction,
                isIncome = transaction.isIncome,
                subcategoryName = subcategoryName,
                parentCategoryName = parentCategoryName,
                color = categoryColorFormatter.format(categoryWithParent?.category),
                formattedDate = dateTimeFormatter.formatForTransaction(transaction.dateTime)
            )
        }
    }

    private fun TransactionCategoryWithParent?.format(): Pair<String, String> {
        return when {
            this == null -> {
                val parentCategoryName = resourceManger.getString(CoreR.string.no_category)
                parentCategoryName to EMPTY_STRING
            }
            parentCategory != null -> {
                parentCategory!!.name to category.name
            }
            else -> category.name to EMPTY_STRING
        }
    }

}