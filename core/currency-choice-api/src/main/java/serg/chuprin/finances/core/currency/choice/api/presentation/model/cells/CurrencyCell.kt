package serg.chuprin.finances.core.currency.choice.api.presentation.model.cells

import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell
import java.util.*

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
data class CurrencyCell(
    val currency: Currency,
    val isChosen: Boolean,
    val displayName: String
) : DiffCell<Currency> {

    override val diffCellId: Currency = currency

}