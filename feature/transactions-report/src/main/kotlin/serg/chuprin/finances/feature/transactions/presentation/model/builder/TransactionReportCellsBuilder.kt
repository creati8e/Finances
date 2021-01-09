package serg.chuprin.finances.feature.transactions.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder.DateTimeFormattingMode
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.*
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.domain.model.TransactionsReport
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartListCell
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodSummaryCell
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.12.2020.
 */
class TransactionReportCellsBuilder @Inject constructor(
    private val resourceManger: ResourceManger,
    private val amountFormatter: AmountFormatter,
    private val dateTimeFormatter: DateTimeFormatter,
    private val transactionCellBuilder: TransactionCellBuilder,
    private val chartCellsBuilder: TransactionReportChartCellsBuilder,
    private val categorySharesCellBuilder: TransactionReportCategorySharesCellBuilder
) {

    fun build(report: TransactionsReport): List<BaseCell> {

        val currency = report.currentUser.defaultCurrency
        val moneyAccounts = report.preparedData.moneyAccounts
        val dataPeriodAmount = report.preparedData.dataPeriodAmount
        val transactionsGroupedByDay = report.preparedData.dataPeriodTransactions

        return buildList {

            val categorySharesCell = report.preparedData
                .categorySharesChart
                ?.let(categorySharesCellBuilder::build)

            buildDataPeriodAmountChartCells(report)?.let { dataPeriodAmountChartCell ->
                add(dataPeriodAmountChartCell)

                if (categorySharesCell != null) {
                    add(SpaceCell(sizeDp = 24))
                }
            }

            categorySharesCell?.let(::add)

            if (transactionsGroupedByDay.isEmpty()) {
                add(buildZeroDataCell())
            } else {
                add(buildDataPeriodSummary(dataPeriodAmount, currency))

                for ((localDate, transactionsWithCategories) in transactionsGroupedByDay.entries) {
                    add(buildDividerCell(localDate))

                    for ((transaction, category) in transactionsWithCategories) {
                        add(buildTransactionCell(transaction, category, moneyAccounts))
                    }
                }
            }
        }
    }

    private fun buildTransactionCell(
        transaction: Transaction,
        category: CategoryWithParent?,
        moneyAccounts: Map<Id, MoneyAccount>
    ): TransactionCell {
        return transactionCellBuilder.build(
            transaction = transaction,
            categoryWithParent = category,
            dateTimeFormattingMode = DateTimeFormattingMode.ONLY_TIME,
            moneyAccount = moneyAccounts.getValue(transaction.moneyAccountId)
        )
    }

    private fun buildDividerCell(localDate: LocalDate): DateDividerCell {
        return DateDividerCell(
            localDate = localDate,
            dateFormatted = dateTimeFormatter.formatAsDay(localDate)
        )
    }

    private fun buildZeroDataCell(): ZeroDataCell {
        return ZeroDataCell(
            iconRes = null,
            fillParent = false,
            contentMessageRes = null,
            titleRes = R.string.transactions_report_zero_data_title
        )
    }

    private fun buildDataPeriodSummary(
        amount: BigDecimal,
        currency: Currency
    ): TransactionReportDataPeriodSummaryCell {
        val formattedAmount = amountFormatter.format(
            amount = amount,
            round = false,
            withSign = true,
            currency = currency,
            withCurrencySymbol = true
        )
        val title = resourceManger.getString(R.string.transactions_report_data_period_summary_title)
        return TransactionReportDataPeriodSummaryCell(title = title, value = formattedAmount)
    }

    private fun buildDataPeriodAmountChartCells(
        report: TransactionsReport
    ): TransactionReportChartListCell? {
        val dataPeriodAmountChartCells = chartCellsBuilder.build(
            currency = report.preparedData.currency,
            dataPeriodAmounts = report.preparedData.dataPeriodAmounts,
            currentDataPeriod = report.filter.reportDataPeriod.dataPeriod
        )
        if (dataPeriodAmountChartCells.isEmpty()) {
            return null
        }
        return TransactionReportChartListCell(dataPeriodAmountChartCells)
    }

}