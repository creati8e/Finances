package serg.chuprin.finances.core.api.domain.model

import java.util.*

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
data class DataPeriod(
    val startDate: Date,
    val endDate: Date,
    val periodType: DataPeriodType
) {

    operator fun contains(date: Date): Boolean {
        return date.after(startDate) && date.before(endDate)
    }

}

