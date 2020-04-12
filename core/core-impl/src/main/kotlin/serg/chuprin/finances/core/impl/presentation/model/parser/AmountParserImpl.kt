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
        val groupSeparator = decimalFormat.groupingSeparator
        val decimalSeparator = decimalFormat.decimalSeparator

        if (!isCorrectAmount(amount, decimalSeparator, groupSeparator)) {
            return null
        }
        var normalizedAmount = amount.replace(("\\$groupSeparator").toRegex(), EMPTY_STRING)
        if (normalizedAmount.isEmpty()) {
            return null
        }
        if (normalizedAmount.last() == decimalSeparator) {
            normalizedAmount = normalizedAmount.substring(0, normalizedAmount.lastIndex)
        }
        return BigDecimal(normalizedAmount.replace(",", "."))
    }

    private fun isCorrectAmount(
        amount: String,
        decimalSeparator: Char,
        groupSeparator: Char
    ): Boolean {
        return when {
            amount.isEmpty() -> false
            // TODO: Take a loot at '0'.
            amount.all { it == decimalSeparator || it == '0' || it == groupSeparator } -> false
            else -> true
        }
    }

}