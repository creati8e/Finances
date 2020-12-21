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
            DataPeriodType.DAY -> {
                val nextStartDate = startDate.plusDays(1).startOfDay()
                DataPeriod(
                    periodType = periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.endOfDay()
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
            DataPeriodType.MONTH -> {
                val nextStartDate = startDate.firstDayOfNextMonth()
                DataPeriod(
                    periodType = periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.lastDayOfMonth()
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
        }
    }

    fun previous(): DataPeriod {
        return when (periodType) {
            DataPeriodType.DAY -> minusDays(1)
            DataPeriodType.WEEK -> minusWeeks(1)
            DataPeriodType.MONTH -> minusMonths(1)
            DataPeriodType.YEAR -> minusYears(1)
        }
    }

    fun minusPeriods(count: Long): DataPeriod {
        return when (periodType) {
            DataPeriodType.DAY -> minusDays(count)
            DataPeriodType.WEEK -> minusWeeks(count)
            DataPeriodType.MONTH -> minusMonths(count)
            DataPeriodType.YEAR -> minusYears(count)
        }
    }

    private fun minusDays(count: Long): DataPeriod {
        val previousStartDate = startDate.minusDays(count).startOfDay()
        return DataPeriod(
            periodType = periodType,
            startDate = previousStartDate,
            endDate = previousStartDate.endOfDay()
        )
    }

    private fun minusWeeks(count: Long): DataPeriod {
        val previousStartDate = startDate.minusWeeks(count).startOfWeek()
        return DataPeriod(
            periodType = periodType,
            startDate = previousStartDate,
            endDate = previousStartDate.endOfWeek()
        )
    }

    private fun minusMonths(count: Long): DataPeriod {
        val previousStartDate = startDate.minusMonths(count).firstDayOfMonth()
        return DataPeriod(
            periodType = periodType,
            startDate = previousStartDate,
            endDate = previousStartDate.lastDayOfMonth()
        )
    }

    private fun minusYears(count: Long): DataPeriod {
        val previousStartDate = startDate.minusYears(count).startOfYear()
        return DataPeriod(
            periodType = periodType,
            startDate = previousStartDate,
            endDate = previousStartDate.endOfYear()
        )
    }

}

