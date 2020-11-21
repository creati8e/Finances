package serg.chuprin.finances.core.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
internal class AmountFormatterImpl @Inject constructor() : AmountFormatter {

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

    override fun formatInput(input: String, currency: Currency): String {
        setupFormatter(currency)

        if (input.isEmpty()) {
            return "0"
        }

        // User can type both ',' and '.' symbols.
        // Do not accept symbol if it is not decimal separator.
        if (!input.last().isDigit() && input.last() != decimalSeparator) {
            return input.dropLast(1)
        }

        // Do not allow multiple decimal separators.
        if (input.count { char -> char == decimalSeparator } > 1) {
            return input.dropLast(1)
        }

        // If last char is separator, parsing is unavailable.
        val normalizedStr = input.replace(("\\$groupSeparator").toRegex(), EMPTY_STRING)
        if (normalizedStr.isEmpty() || normalizedStr.last() == decimalSeparator) {
            return input
        }

        val decimalSeparatorIndex = normalizedStr.indexOf(decimalSeparator)
        if (normalizedStr.last() == '0'
            && decimalSeparatorIndex != -1
            && (normalizedStr.length - (decimalSeparatorIndex + 1))
            < numberFormat.maximumFractionDigits
        ) {
            return input
        }
        return try {
            val bigDecimal = if (normalizedStr.contains(decimalSeparator, true)) {
                val replaced = normalizedStr.replace(",", ".")
                if (getFractionDigitsCount(replaced) > numberFormat.maximumFractionDigits) {
                    BigDecimal(replaced.dropLast(1))
                } else {
                    BigDecimal(replaced)
                }
            } else {
                BigDecimal(normalizedStr)
            }
            numberFormat.format(bigDecimal)
        } catch (e: NumberFormatException) {
            input
        }
    }

    override fun format(
        amount: BigDecimal,
        currency: Currency,
        round: Boolean,
        withCurrencySymbol: Boolean,
        withSign: Boolean
    ): String {

        fun DecimalFormat.addPrefixIfNeeded() {
            if (!withSign) {
                return
            }
            positivePrefix = when (amount.signum()) {
                1 -> "+"
                -1 -> "+"
                else -> ""
            }
        }

        val locale = Locale.getDefault()
        if (!withCurrencySymbol) {
            val instance = (NumberFormat.getInstance(locale) as DecimalFormat).apply {
                maximumFractionDigits = currency.defaultFractionDigits
                minimumFractionDigits = currency.defaultFractionDigits
                addPrefixIfNeeded()
            }

            return instance.format(amount)
        }
        return (NumberFormat.getCurrencyInstance(locale) as DecimalFormat).apply {
            decimalFormatSymbols = DecimalFormatSymbols().apply {
                currencySymbol = currency.symbol
                internationalCurrencySymbol = currency.symbol
            }
            addPrefixIfNeeded()
            maximumFractionDigits = when (round) {
                true -> 0
                else -> currency.defaultFractionDigits
            }
        }.format(amount)
    }

    private fun getFractionDigitsCount(numberStr: String): Int {
        var count = 0
        var reachedDot = false
        for (c in numberStr) {
            if (reachedDot) {
                ++count
            } else if (c == '.') {
                reachedDot = true
            }
        }
        return count
    }

    private fun setupFormatter(currency: Currency) {
        numberFormat.currency = currency
    }

}