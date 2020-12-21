package serg.chuprin.finances.core.api.extensions

import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoField
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

/**
 * Created by Sergey Chuprin on 18.04.2020.
 */
fun Date.toLocalDateTimeUTC(): LocalDateTime {
    return Instant
        .ofEpochMilli(time)
        .atOffset(ZoneOffset.UTC)
        .toLocalDateTime()
}

fun LocalDateTime.toDateUTC(): Date {
    return Date.from(toInstant(ZoneOffset.UTC))
}

// region Month adjustment.

fun LocalDateTime.lastDayOfMonth(): LocalDateTime {
    return with(TemporalAdjusters.lastDayOfMonth()).endOfDay()
}

fun LocalDateTime.firstDayOfNextMonth(): LocalDateTime {
    return with(TemporalAdjusters.firstDayOfNextMonth()).startOfDay()
}

fun LocalDateTime.firstDayOfMonth(): LocalDateTime {
    return with(TemporalAdjusters.firstDayOfMonth()).startOfDay()
}

// endregion


// region Day adjustment.

fun LocalDateTime.startOfDay(): LocalDateTime {
    return with(ChronoField.NANO_OF_DAY, LocalTime.MIDNIGHT.toNanoOfDay())
}

fun LocalDateTime.endOfDay(): LocalDateTime {
    return with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay())
}

// endregion


// region Week adjustment.

fun LocalDateTime.startOfWeek(): LocalDateTime {
    return with(WeekFields.of(Locale.getDefault()).firstDayOfWeek).startOfDay()
}

fun LocalDateTime.endOfWeek(): LocalDateTime = plusDays(6).endOfDay()

// endregion


// region Year adjustment.

fun LocalDateTime.startOfYear(): LocalDateTime {
    return with(TemporalAdjusters.firstDayOfYear()).startOfDay()
}

fun LocalDateTime.endOfYear(): LocalDateTime {
    return with(TemporalAdjusters.lastDayOfYear()).endOfDay()
}

fun LocalDateTime.startOfNextYear(): LocalDateTime {
    return with(TemporalAdjusters.firstDayOfNextYear()).startOfDay()
}

// endregion

fun LocalDateTime.adjustToTheEndOfPeriod(dataPeriodType: DataPeriodType): LocalDateTime {
    return when (dataPeriodType) {
        DataPeriodType.DAY -> endOfDay()
        DataPeriodType.WEEK -> endOfWeek()
        DataPeriodType.MONTH -> lastDayOfMonth()
        DataPeriodType.YEAR -> endOfYear()
    }
}

fun LocalDateTime.adjustToTheStartOfPeriod(dataPeriodType: DataPeriodType): LocalDateTime {
    return when (dataPeriodType) {
        DataPeriodType.DAY -> startOfDay()
        DataPeriodType.WEEK -> startOfWeek()
        DataPeriodType.MONTH -> firstDayOfMonth()
        DataPeriodType.YEAR -> startOfYear()
    }
}