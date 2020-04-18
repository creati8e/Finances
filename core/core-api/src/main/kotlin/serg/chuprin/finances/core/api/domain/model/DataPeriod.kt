package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.extensions.firstDayOfMonth
import serg.chuprin.finances.core.api.extensions.firstDayOfNextMonth
import serg.chuprin.finances.core.api.extensions.firstDayOfPreviousMonth
import serg.chuprin.finances.core.api.extensions.lastDayOfMonth
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
data class DataPeriod(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val periodType: DataPeriodType
) {

    companion object {

        fun from(periodType: DataPeriodType): DataPeriod {
            return when (periodType) {
                DataPeriodType.MONTH -> {
                    val startDateTime = LocalDateTime.now().firstDayOfMonth()
                    DataPeriod(startDateTime, startDateTime.lastDayOfMonth(), periodType)
                }
            }
        }

    }

    operator fun contains(dateTime: LocalDateTime): Boolean {
        return dateTime.isAfter(startDate) && dateTime.isBefore(endDate)
    }

    fun next(): DataPeriod {
        return when (periodType) {
            DataPeriodType.MONTH -> {
                val startDateTime = startDate.firstDayOfNextMonth()
                DataPeriod(startDateTime, startDate.lastDayOfMonth(), periodType)
            }
        }
    }

    fun previous(): DataPeriod {
        return when (periodType) {
            DataPeriodType.MONTH -> {
                val startDateTime = startDate.firstDayOfPreviousMonth()
                DataPeriod(startDateTime, startDate.lastDayOfMonth(), periodType)
            }
        }
    }

}

