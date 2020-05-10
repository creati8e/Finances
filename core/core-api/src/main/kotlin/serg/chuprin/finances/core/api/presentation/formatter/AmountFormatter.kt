package serg.chuprin.finances.core.api.presentation.formatter

import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
interface AmountFormatter {

    /**
     * Formats raw user's input.
     * @return string formatted with [currency] respecting current locale's rules.
     */
    fun formatInput(input: String, currency: Currency): String

    fun format(
        amount: BigDecimal,
        currency: Currency,
        round: Boolean = true,
        withCurrencySymbol: Boolean = true,
        withSign: Boolean = false
    ): String

}