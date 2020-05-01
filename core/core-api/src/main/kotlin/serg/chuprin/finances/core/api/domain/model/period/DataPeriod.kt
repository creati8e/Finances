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
                        periodType = periodType,
                        startDate = startDateTime,
                        endDate = startDateTime.lastDayOfMonth()
                    )
                }
                DataPeriodType.DAY -> {
                    val startDateTime = LocalDateTime.now().startOfDay()
                    DataPeriod(
                        periodType = periodType,
                        startDate = startDateTime,
                        endDate = startDateTime.endOfDay()
                    )
                }
                DataPeriodType.WEEK -> {
                    val startDateTime = LocalDateTime.now().startOfWeek()
                    DataPeriod(
                        periodType = periodType,
                        startDate = startDateTime,
                        endDate = startDateTime.endOfWeek()
                    )
                }
                DataPeriodType.YEAR -> {
                    val startDateTime = LocalDateTime.now().startOfYear()
                    DataPeriod(
                        periodType = periodType,
                        startDate = startDateTime,
                        endDate = startDateTime.endOfYear()
                    )
                }
            }
        }

    }

    operator fun contains(dateTime: LocalDateTime): Boolean {
        return (dateTime.isEqual(startDate) || dateTime.isAfter(startDate))
                && (dateTime.isEqual(endDate) || dateTime.isBefore(endDate))
    }

    fun next(): DataPeriod {
        return when (periodType) {
            DataPeriodType.MONTH -> {
                val nextStartDate = startDate.firstDayOfNextMonth()
                DataPeriod(
                    periodType = periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.lastDayOfMonth()
                )
            }
            DataPeriodType.WEEK -> {
                val nextStartDate = startDate.plusWeeks(1).startOfWeek()
                DataPeriod(
                    periodType = periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.endOfWeek()
                )
            }
            DataPeriodType.YEAR -> {
                val nextStartDate = startDate.startOfNextYear()
                DataPeriod(
                    periodType = periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.endOfYear()
                )
            }
            DataPeriodType.DAY -> {
                val nextStartDate = startDate.plusDays(1).startOfDay()
                DataPeriod(
                    periodType = periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.endOfDay()
                )
            }
        }
    }

    fun previous(): DataPeriod {
        return when (periodType) {
            DataPeriodType.MONTH -> {
                val nextStartDate = startDate.minusMonths(1).firstDayOfMonth()
                DataPeriod(
                    periodType = periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.lastDayOfMonth()
                )
            }
            DataPeriodType.DAY -> {
                val nextStartDate = startDate.minusDays(1).startOfDay()
                DataPeriod(
                    periodType = periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.endOfDay()
                )
            }
            DataPeriodType.WEEK -> {
                val nextStartDate = startDate.minusWeeks(1).startOfWeek()
                DataPeriod(
                    periodType = periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.endOfWeek()
                )
            }
            DataPeriodType.YEAR -> {
                val nextStartDate = startDate.minusYears(1).startOfYear()
                DataPeriod(
                    periodType = periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.endOfYear()
                )
            }
        }
    }

}

