package serg.chuprin.finances.core.api.presentation.formatter

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
interface DateTimeFormatter {

    fun formatTime(dateTime: LocalDateTime): String

    fun formatForTransaction(localDate: LocalDate): String

    fun formatForTransaction(dateTime: LocalDateTime): String

}