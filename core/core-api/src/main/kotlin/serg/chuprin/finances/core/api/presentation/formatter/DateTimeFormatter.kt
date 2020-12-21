package serg.chuprin.finances.core.api.presentation.formatter

import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
interface DateTimeFormatter {

    fun formatTime(dateTime: LocalDateTime): String

    fun formatAsDay(localDate: LocalDate): String

    fun formatDataPeriod(dataPeriod: DataPeriod): String

    fun formatForTransaction(dateTime: LocalDateTime): String

}