package serg.chuprin.finances.core.api.domain.model.period

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
            return DataPeriodBuilder.fromStartDate(startDate, periodType)
        }

        fun from(periodType: DataPeriodType): DataPeriod {
            return DataPeriodBuilder.fromPeriodType(periodType)
        }

    }

    operator fun contains(dateTime: LocalDateTime): Boolean = dateTime in startDate..endDate

    fun next(): DataPeriod = DataPeriodBuilder.next(this)

    fun previous(): DataPeriod = DataPeriodBuilder.previous(this)

    fun minusPeriods(count: Long): DataPeriod = DataPeriodBuilder.minusPeriods(this, count)

}

