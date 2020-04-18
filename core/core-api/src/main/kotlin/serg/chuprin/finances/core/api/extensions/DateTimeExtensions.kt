package serg.chuprin.finances.core.api.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters
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

fun LocalDateTime.lastDayOfMonth(): LocalDateTime {
    return with(TemporalAdjusters.lastDayOfMonth())
}

fun LocalDateTime.firstDayOfNextMonth(): LocalDateTime {
    return with(TemporalAdjusters.firstDayOfNextMonth())
}

fun LocalDateTime.firstDayOfMonth(): LocalDateTime {
    return with(TemporalAdjusters.firstDayOfMonth())
}

fun LocalDateTime.firstDayOfPreviousMonth(): LocalDateTime {
    return minusMonths(1).with(TemporalAdjusters.firstDayOfMonth())
}