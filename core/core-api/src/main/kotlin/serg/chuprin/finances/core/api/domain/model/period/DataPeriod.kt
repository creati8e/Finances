package serg.chuprin.finances.core.api.domain.model.period

import serg.chuprin.finances.core.api.extensions.*
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
                    DataPeriod(
                        startDateTime,
                        startDateTime.lastDayOfMonth(),
                        periodType
                    )
                }
                DataPeriodType.DAY -> {
                    val startDateTime = LocalDateTime.now().startOfDay()
                    DataPeriod(
                        startDateTime,
                        startDateTime.endOfDay(),
                        periodType
                    )
                }
                DataPeriodType.WEEK -> {
                    val startDateTime = LocalDateTime.now().startOfWeek()
                    DataPeriod(
                        startDateTime,
                        startDateTime.endOfWeek(),
                        periodType
                    )
                }
                DataPeriodType.YEAR -> {
                    val startDateTime = LocalDateTime.now().startOfYear()
                    DataPeriod(
                        startDateTime,
                        startDateTime.endOfYear(),
                        periodType
                    )
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
                val nextStartDate = startDate.firstDayOfNextMonth()
                DataPeriod(
                    nextStartDate,
                    nextStartDate.lastDayOfMonth(),
                    periodType
                )
            }
            DataPeriodType.WEEK -> {
                val nextStartDate = startDate.plusWeeks(1).startOfWeek()
                DataPeriod(
                    nextStartDate,
                    nextStartDate.endOfWeek(),
                    periodType
                )
            }
            DataPeriodType.YEAR -> {
                val nextStartDate = startDate.startOfNextYear()
                DataPeriod(
                    nextStartDate,
                    nextStartDate.endOfYear(),
                    periodType
                )
            }
            DataPeriodType.DAY -> {
                val nextStartDate = startDate.plusDays(1).startOfDay()
                DataPeriod(
                    nextStartDate,
                    nextStartDate.endOfDay(),
                    periodType
                )
            }
        }
    }

    fun previous(): DataPeriod {
        return when (periodType) {
            DataPeriodType.MONTH -> {
                val nextStartDate = startDate.minusMonths(1).firstDayOfMonth()
                DataPeriod(
                    nextStartDate,
                    nextStartDate.lastDayOfMonth(),
                    periodType
                )
            }
            DataPeriodType.DAY -> {
                val nextStartDate = startDate.minusDays(1).startOfDay()
                DataPeriod(
                    nextStartDate,
                    nextStartDate.endOfDay(),
                    periodType
                )
            }
            DataPeriodType.WEEK -> {
                val nextStartDate = startDate.minusWeeks(1).startOfWeek()
                DataPeriod(
                    nextStartDate,
                    nextStartDate.endOfWeek(),
                    periodType
                )
            }
            DataPeriodType.YEAR -> {
                val nextStartDate = startDate.minusYears(1).startOfYear()
                DataPeriod(
                    nextStartDate,
                    nextStartDate.endOfYear(),
                    periodType
                )
            }
        }
    }

}

