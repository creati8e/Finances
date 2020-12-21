package serg.chuprin.finances.core.api.domain.model.period

import serg.chuprin.finances.core.api.extensions.*
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
// TODO: Make iterable and comparable.
data class DataPeriod(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val periodType: DataPeriodType
) {

    companion object {

        fun fromStartDate(
            startDate: LocalDateTime,
            periodType: DataPeriodType
        ): DataPeriod {
            return when (periodType) {
                DataPeriodType.DAY -> buildDay(startDate)
                DataPeriodType.WEEK -> buildWeek(startDate)
                DataPeriodType.MONTH -> buildMonth(startDate)
                DataPeriodType.YEAR -> buildYear(startDate)
            }
        }

        fun from(periodType: DataPeriodType): DataPeriod {
            return when (periodType) {
                DataPeriodType.DAY -> buildDay(LocalDateTime.now())
                DataPeriodType.WEEK -> buildWeek(LocalDateTime.now())
                DataPeriodType.MONTH -> buildMonth(LocalDateTime.now())
                DataPeriodType.YEAR -> buildYear(LocalDateTime.now())
            }
        }

        private fun buildDay(startDate: LocalDateTime): DataPeriod {
            val startDateTime = startDate.startOfDay()
            return DataPeriod(
                startDate = startDateTime,
                periodType = DataPeriodType.DAY,
                endDate = startDateTime.endOfDay()
            )
        }

        private fun buildWeek(startDate: LocalDateTime): DataPeriod {
            val startDateTime = startDate.startOfWeek()
            return DataPeriod(
                periodType = DataPeriodType.WEEK,
                startDate = startDateTime,
                endDate = startDateTime.endOfWeek()
            )
        }

        private fun buildMonth(startDate: LocalDateTime): DataPeriod {
            val startDateTime = startDate.firstDayOfMonth()
            return DataPeriod(
                startDate = startDateTime,
                periodType = DataPeriodType.MONTH,
                endDate = startDateTime.lastDayOfMonth()
            )
        }

        private fun buildYear(startDate: LocalDateTime): DataPeriod {
            val startDateTime = startDate.startOfYear()
            return DataPeriod(
                startDate = startDateTime,
                periodType = DataPeriodType.YEAR,
                endDate = startDateTime.endOfYear()
            )
        }

    }

    operator fun contains(dateTime: LocalDateTime): Boolean = dateTime in startDate..endDate

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

