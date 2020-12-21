package serg.chuprin.finances.feature.transactions.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartCell
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
class TransactionReportChartCellsBuilder @Inject constructor(
    private val amountFormatter: AmountFormatter,
    private val dateTimeFormatter: DateTimeFormatter
) {

    fun build(
        chartData: Map<DataPeriod, BigDecimal>,
        currency: Currency
    ): List<TransactionReportChartCell> {
        return chartData.map { (dataPeriod, amount) ->
            TransactionReportChartCell(
                dataPeriod = dataPeriod,
                formattedPeriodName = dateTimeFormatter.formatDataPeriod(dataPeriod),
                formattedAmount = amountFormatter.format(
                    amount = amount,
                    round = false,
                    currency = currency,
                    withCurrencySymbol = false
                )
            )
        }
    }

}