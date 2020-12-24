package serg.chuprin.finances.feature.transactions.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportDataPeriodAmountChartCell
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
class TransactionReportChartCellsBuilder @Inject constructor(
    private val amountFormatter: AmountFormatter,
    private val dateTimeFormatter: DateTimeFormatter
) {

    private companion object {
        private val minPercent = BigDecimal(0.01)
        private val totalPercent = BigDecimal(100)
    }

    fun build(
        currentDataPeriod: DataPeriod?,
        dataPeriodAmounts: Map<DataPeriod, BigDecimal>,
        currency: Currency
    ): List<TransactionReportDataPeriodAmountChartCell> {
        if (dataPeriodAmounts.isEmpty()) {
            return emptyList()
        }
        val maxAmount = dataPeriodAmounts.maxOf { (_, amount) -> amount.abs() }
        return dataPeriodAmounts.map { (dataPeriod, amount) ->
            TransactionReportDataPeriodAmountChartCell(
                dataPeriod = dataPeriod,
                formattedPeriodName = dateTimeFormatter.formatDataPeriod(dataPeriod),
                formattedAmount = amountFormatter.format(
                    amount = amount,
                    round = false,
                    currency = currency,
                    withCurrencySymbol = false
                ),
                isChosen = dataPeriod == currentDataPeriod,
                barFill = calculatePercent(max = maxAmount, target = amount)
            )
        }
    }

    private fun calculatePercent(max: BigDecimal, target: BigDecimal): Int {
        if (max == BigDecimal.ZERO) return 0

        val rawPercent = target.abs().divide(max, 2, RoundingMode.HALF_EVEN)
        if (rawPercent < minPercent) return 0

        val long = rawPercent.multiply(totalPercent).longValueExact()
        return if (long > 100) 100 else long.toInt()
    }

}