package serg.chuprin.finances.core.api.presentation.model.cells

import java.time.LocalDate

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
data class DateDividerCell(
    val dateFormatted: String,
    val localDate: LocalDate
) : DiffCell<LocalDate> {

    override val diffCellId: LocalDate = localDate

}