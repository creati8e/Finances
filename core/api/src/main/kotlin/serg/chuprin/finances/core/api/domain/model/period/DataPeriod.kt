package serg.chuprin.finances.core.api.domain.model.period

import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
data class DataPeriod(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val periodType: DataPeriodType
) : Comparable<DataPeriod> {

    companion object {

        fun fromStartDate(
            startDate: LocalDateTime,
            periodType: DataPeriodType
        ): DataPeriod {
            return DataPeriodBuilder.fromStartDate(startDate, periodType)
        }

        fun fromEndDate(
            endDate: LocalDateTime,
            periodType: DataPeriodType
        ): DataPeriod {
            return DataPeriodBuilder.fromEndDate(endDate, periodType)
        }

        fun from(periodType: DataPeriodType): DataPeriod {
            return DataPeriodBuilder.fromPeriodType(periodType)
        }

    }

    // region Operators.

    operator fun inc(): DataPeriod = next()

    operator fun contains(dateTime: LocalDateTime): Boolean = dateTime in startDate..endDate

    operator fun rangeTo(that: DataPeriod) = DataPeriodRange(start = this, endInclusive = that)

    // endregion

    override fun compareTo(other: DataPeriod): Int {
        require(other.periodType == periodType) {
            "Data periods with different period types can not be compared"
        }
        return startDate.compareTo(other.startDate)
    }

    fun next(): DataPeriod = DataPeriodBuilder.next(this)

    fun previous(): DataPeriod = DataPeriodBuilder.previous(this)

    fun minusPeriods(count: Long): DataPeriod = DataPeriodBuilder.minusPeriods(this, count)

}

