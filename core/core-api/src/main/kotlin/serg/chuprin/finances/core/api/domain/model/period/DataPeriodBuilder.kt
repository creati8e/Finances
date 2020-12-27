package serg.chuprin.finances.core.api.domain.model.period

import serg.chuprin.finances.core.api.extensions.*
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 27.12.2020.
 */
internal object DataPeriodBuilder {

    fun fromStartDate(
        startDate: LocalDateTime,
        periodType: DataPeriodType
    ): DataPeriod {
        return when (periodType) {
            DataPeriodType.DAY -> buildForDayStart(startDate)
            DataPeriodType.WEEK -> buildForWeekStart(startDate)
            DataPeriodType.MONTH -> buildForMonthStart(startDate)
            DataPeriodType.YEAR -> buildForYearStart(startDate)
        }
    }

    fun fromEndDate(endDate: LocalDateTime, periodType: DataPeriodType): DataPeriod {
        return when (periodType) {
            DataPeriodType.DAY -> buildForDayEnd(endDate)
            DataPeriodType.WEEK -> buildForWeekEnd(endDate)
            DataPeriodType.MONTH -> buildForMonthEnd(endDate)
            DataPeriodType.YEAR -> buildForYearEnd(endDate)
        }
    }

    fun fromPeriodType(periodType: DataPeriodType): DataPeriod {
        return when (periodType) {
            DataPeriodType.DAY -> buildForDayStart(LocalDateTime.now())
            DataPeriodType.WEEK -> buildForWeekStart(LocalDateTime.now())
            DataPeriodType.MONTH -> buildForMonthStart(LocalDateTime.now())
            DataPeriodType.YEAR -> buildForYearStart(LocalDateTime.now())
        }
    }

    fun next(dataPeriod: DataPeriod): DataPeriod {
        return when (dataPeriod.periodType) {
            DataPeriodType.DAY -> {
                val nextStartDate = dataPeriod.startDate.plusDays(1).startOfDay()
                DataPeriod(
                    periodType = dataPeriod.periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.endOfDay()
                )
            }
            DataPeriodType.WEEK -> {
                val nextStartDate = dataPeriod.startDate.plusWeeks(1).startOfWeek()
                DataPeriod(
                    periodType = dataPeriod.periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.endOfWeek()
                )
            }
            DataPeriodType.MONTH -> {
                val nextStartDate = dataPeriod.startDate.firstDayOfNextMonth()
                DataPeriod(
                    periodType = dataPeriod.periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.lastDayOfMonth()
                )
            }
            DataPeriodType.YEAR -> {
                val nextStartDate = dataPeriod.startDate.startOfNextYear()
                DataPeriod(
                    periodType = dataPeriod.periodType,
                    startDate = nextStartDate,
                    endDate = nextStartDate.endOfYear()
                )
            }
        }
    }

    fun previous(dataPeriod: DataPeriod): DataPeriod {
        return when (dataPeriod.periodType) {
            DataPeriodType.DAY -> dataPeriod.minusDays(1)
            DataPeriodType.WEEK -> dataPeriod.minusWeeks(1)
            DataPeriodType.MONTH -> dataPeriod.minusMonths(1)
            DataPeriodType.YEAR -> dataPeriod.minusYears(1)
        }
    }

    fun minusPeriods(dataPeriod: DataPeriod, count: Long): DataPeriod {
        return when (dataPeriod.periodType) {
            DataPeriodType.DAY -> dataPeriod.minusDays(count)
            DataPeriodType.WEEK -> dataPeriod.minusWeeks(count)
            DataPeriodType.MONTH -> dataPeriod.minusMonths(count)
            DataPeriodType.YEAR -> dataPeriod.minusYears(count)
        }
    }

    // region Mutators.

    private fun DataPeriod.minusDays(count: Long): DataPeriod {
        val previousStartDate = startDate.minusDays(count).startOfDay()
        return DataPeriod(
            periodType = periodType,
            startDate = previousStartDate,
            endDate = previousStartDate.endOfDay()
        )
    }

    private fun DataPeriod.minusWeeks(count: Long): DataPeriod {
        val previousStartDate = startDate.minusWeeks(count).startOfWeek()
        return DataPeriod(
            periodType = periodType,
            startDate = previousStartDate,
            endDate = previousStartDate.endOfWeek()
        )
    }

    private fun DataPeriod.minusMonths(count: Long): DataPeriod {
        val previousStartDate = startDate.minusMonths(count).firstDayOfMonth()
        return DataPeriod(
            periodType = periodType,
            startDate = previousStartDate,
            endDate = previousStartDate.lastDayOfMonth()
        )
    }

    private fun DataPeriod.minusYears(count: Long): DataPeriod {
        val previousStartDate = startDate.minusYears(count).startOfYear()
        return DataPeriod(
            periodType = periodType,
            startDate = previousStartDate,
            endDate = previousStartDate.endOfYear()
        )
    }


    // endregion

    // region Builders.

    private fun buildForDayStart(startDate: LocalDateTime): DataPeriod {
        val startDateTime = startDate.startOfDay()
        return DataPeriod(
            startDate = startDateTime,
            periodType = DataPeriodType.DAY,
            endDate = startDateTime.endOfDay()
        )
    }

    private fun buildForWeekStart(startDate: LocalDateTime): DataPeriod {
        val startDateTime = startDate.startOfWeek()
        return DataPeriod(
            periodType = DataPeriodType.WEEK,
            startDate = startDateTime,
            endDate = startDateTime.endOfWeek()
        )
    }

    private fun buildForMonthStart(startDate: LocalDateTime): DataPeriod {
        val startDateTime = startDate.firstDayOfMonth()
        return DataPeriod(
            startDate = startDateTime,
            periodType = DataPeriodType.MONTH,
            endDate = startDateTime.lastDayOfMonth()
        )
    }

    private fun buildForYearStart(startDate: LocalDateTime): DataPeriod {
        val startDateTime = startDate.startOfYear()
        return DataPeriod(
            startDate = startDateTime,
            periodType = DataPeriodType.YEAR,
            endDate = startDateTime.endOfYear()
        )
    }

    private fun buildForDayEnd(endDate: LocalDateTime): DataPeriod {
        val endDateTime = endDate.endOfDay()
        return DataPeriod(
            endDate = endDateTime,
            periodType = DataPeriodType.DAY,
            startDate = endDateTime.startOfDay()
        )
    }

    private fun buildForWeekEnd(endDate: LocalDateTime): DataPeriod {
        val endDateTime = endDate.endOfWeek()
        return DataPeriod(
            endDate = endDateTime,
            periodType = DataPeriodType.WEEK,
            startDate = endDateTime.startOfWeek()
        )
    }

    private fun buildForMonthEnd(endDate: LocalDateTime): DataPeriod {
        val endDateTime = endDate.lastDayOfMonth()
        return DataPeriod(
            endDate = endDateTime,
            periodType = DataPeriodType.MONTH,
            startDate = endDateTime.firstDayOfMonth()
        )
    }

    private fun buildForYearEnd(endDate: LocalDateTime): DataPeriod {
        val endDateTime = endDate.endOfYear()
        return DataPeriod(
            endDate = endDateTime,
            periodType = DataPeriodType.YEAR,
            startDate = endDateTime.startOfYear()
        )
    }

    // endregion

}