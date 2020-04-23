package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import androidx.annotation.ColorInt
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.api.domain.model.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.presentation.formatter.DateFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
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
    private val dateFormatter: DateFormatter,
    private val resourceManger: ResourceManger,
    private val amountFormatter: AmountFormatter
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
            DashboardTransactionCell(
                amount = formattedAmount,
                transaction = transaction,
                amountColor = transaction.formatColor(),
                categoryName = categoryWithParent.format(),
                formattedDate = dateFormatter.formatForTransaction(transaction.dateTime)
            )
        }
    }

    @ColorInt
    private fun Transaction.formatColor(): Int {
        if (isIncome) {
            return resourceManger.getColor(CoreR.color.colorGreen)
        }
        return resourceManger.getThemeColor(android.R.attr.textColorPrimary)
    }

    private fun TransactionCategoryWithParent?.format(): String {
        if (this == null) {
            return resourceManger.getString(CoreR.string.no_category)
        }
        if (parentCategory != null) {
            return "${parentCategory!!.name}\\${category.name}"
        }
        return category.name
    }

}