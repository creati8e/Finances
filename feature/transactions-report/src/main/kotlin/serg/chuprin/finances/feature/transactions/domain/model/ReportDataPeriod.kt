package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
sealed class ReportDataPeriod(
    open val startDate: LocalDateTime?,
    open val endDate: LocalDateTime?
) {

    object AllTime : ReportDataPeriod(null, null) {
        override val dataPeriod: DataPeriod? = null
    }

    data class Predefined(
        override val dataPeriod: DataPeriod
    ) : ReportDataPeriod(dataPeriod.startDate, dataPeriod.endDate)

    sealed class Custom(
        override val startDate: LocalDateTime?,
        override val endDate: LocalDateTime?
    ) : ReportDataPeriod(startDate, endDate) {

        data class WithStartDate(
            override val startDate: LocalDateTime
        ) : Custom(startDate, null)

        data class WithEndDate(
            override val endDate: LocalDateTime
        ) : Custom(null, endDate)

        data class WithBothDates(
            override val startDate: LocalDateTime,
            override val endDate: LocalDateTime
        ) : Custom(startDate, endDate)

        override val dataPeriod: DataPeriod? = null

    }

    abstract val dataPeriod: DataPeriod?

    operator fun contains(dateTime: LocalDateTime): Boolean {
        val startDate = startDate
        val endDate = endDate
        if (startDate == null && endDate == null) {
            return true
        }
        if (startDate == null) {
            return dateTime <= endDate
        }
        if (endDate == null) {
            return dateTime >= startDate
        }
        return dateTime in startDate..endDate
    }

}