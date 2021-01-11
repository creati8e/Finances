package serg.chuprin.finances.core.impl.presentation.model.parser

import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import java.math.BigDecimal
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
internal class AmountParserImpl @Inject constructor() : AmountParser {

    override fun parse(amount: String): BigDecimal? {
        val decimalFormat = DecimalFormatSymbols.getInstance(Locale.getDefault())
        if (!isCorrectAmount(amount, decimalFormat)) {
            return null
        }
        // Remove all grouping symbols.
        val normalizedAmount = amount.replace(
            replacement = EMPTY_STRING,
            regex = ("\\${decimalFormat.groupingSeparator}").toRegex()
        )
        if (normalizedAmount.isEmpty()) {
            return null
        }
        return try {
            BigDecimal(
                if (normalizedAmount.endsWith(decimalFormat.decimalSeparator)) {
                    normalizedAmount.dropLast(1).replace(",", ".")
                } else {
                    normalizedAmount.replace(",", ".")
                }
            )
        } catch (e: NumberFormatException) {
            null
        }
    }

    private fun isCorrectAmount(amount: String, formatSymbols: DecimalFormatSymbols): Boolean {
        return when {
            amount.isEmpty() -> false
            amount.all {
                it == formatSymbols.decimalSeparator || it == formatSymbols.groupingSeparator
            } -> false
            else -> true
        }
    }

}