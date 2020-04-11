package serg.chuprin.finances.core.api.presentation.view

import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import java.math.BigDecimal
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
class AmountFormatter {

    private val groupSeparator: Char
    private val decimalSeparator: Char
    private val numberFormat: NumberFormat
    private val decimalFormat: DecimalFormatSymbols

    init {
        val locale = Locale.getDefault()

        numberFormat = NumberFormat.getInstance(locale)
        decimalFormat = DecimalFormatSymbols.getInstance(locale)
        groupSeparator = decimalFormat.groupingSeparator
        decimalSeparator = decimalFormat.decimalSeparator
    }

    fun format(input: String, currency: Currency): Pair<String, Boolean> {
        setupFormatter(currency)

        var str = input
        if (str.isEmpty()) {
            return "0" to true
        }

        // User can type both ',' and '.' symbols.
        // Do not accept symbol if it is not decimal separator.
        if (!str.last().isDigit() && str.last() != decimalSeparator) {
            return str.substring(0, str.lastIndex) to true
        }

        // Do not allow multiple decimal separators.
        if (str.count { char -> char == decimalSeparator } > 1) {
            str = str.substring(0, str.lastIndex)
        }

        // If last char is separator, parsing is unavailable.
        val normalizedStr = str.replace(("\\$groupSeparator").toRegex(), EMPTY_STRING)
        if (normalizedStr.isEmpty() || normalizedStr.last() == decimalSeparator) {
            return str to true
        }

        val decimalSeparatorIndex = normalizedStr.indexOf(decimalSeparator)
        if (normalizedStr.last() == '0'
            && decimalSeparatorIndex != -1
            && (normalizedStr.length - (decimalSeparatorIndex + 1))
            < numberFormat.maximumFractionDigits
        ) {
            return str to true
        }
        return try {
            val bigDecimal = if (normalizedStr.contains(decimalSeparator, true)) {
                BigDecimal(normalizedStr.replace(",", "."))
            } else {
                BigDecimal(normalizedStr)
            }
            numberFormat.format(bigDecimal) to true
        } catch (e: NumberFormatException) {
            str to false
        }
    }

    private fun normalize(amount: String): String {
        if (!isCorrectAmount(amount)) {
            throw IllegalStateException("Incorrect amount")
        }
        var normalizedAmount = amount.replace(("\\$groupSeparator").toRegex(), EMPTY_STRING)
        if (normalizedAmount.isEmpty()) {
            throw IllegalStateException("Incorrect amount")
        }
        if (normalizedAmount.last() == decimalSeparator) {
            normalizedAmount = normalizedAmount.substring(0, normalizedAmount.lastIndex)
        }
        return normalizedAmount.replace(",", ".")
    }

    private fun setupFormatter(currency: Currency) {
        numberFormat.maximumFractionDigits = currency.defaultFractionDigits
    }

    private fun isCorrectAmount(amount: String): Boolean {
        return when {
            amount.isEmpty() -> false
            amount.all { it == decimalSeparator || it == '0' || it == groupSeparator } -> false
            else -> true
        }
    }

}