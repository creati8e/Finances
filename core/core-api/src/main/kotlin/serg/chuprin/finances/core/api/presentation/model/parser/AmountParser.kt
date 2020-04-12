package serg.chuprin.finances.core.api.presentation.model.parser

import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
interface AmountParser {

    /**
     * @return null if amount is not parsed correctly.
     */
    fun parse(amount: String): BigDecimal?

}