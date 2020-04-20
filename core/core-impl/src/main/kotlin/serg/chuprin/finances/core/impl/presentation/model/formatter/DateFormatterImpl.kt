package serg.chuprin.finances.core.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.presentation.formatter.DateFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
internal class DateFormatterImpl @Inject constructor() : DateFormatter {

    private companion object {
        private val SAME_MONTH_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM")
        private val TODAY_FORMATTER = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        private val DEFAULT_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    }

    override fun formatForTransaction(dateTime: LocalDateTime): String {
        // Check if today.
        val today = LocalDate.now()
        val formatter = when {
            today == dateTime.toLocalDate() -> {
                TODAY_FORMATTER
            }
            today.year == dateTime.year -> {
                if (today.month == dateTime.month) {
                    SAME_MONTH_FORMATTER
                } else {
                    DEFAULT_FORMATTER
                }
            }
            else -> {
                DEFAULT_FORMATTER
            }
        }
        return dateTime.format(formatter.localized())
    }

}