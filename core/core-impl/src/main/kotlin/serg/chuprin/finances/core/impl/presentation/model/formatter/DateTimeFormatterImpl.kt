package serg.chuprin.finances.core.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle
import java.time.temporal.ChronoField
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
internal class DateTimeFormatterImpl @Inject constructor() :
    serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter {

    private companion object {
        private val DAY_FORMATTER = DateTimeFormatter.ofPattern("d\nMMM")
        private val WEEK_FORMATTER = DateTimeFormatter.ofPattern("d\nMMM")
        private val MONTH_NAME_FORMATTER = DateTimeFormatter.ofPattern("MMM")

        private val TIME_FORMATTER = DateTimeFormatterBuilder()
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .toFormatter()

        private val TODAY_TIME_FORMATTER = TIME_FORMATTER
        private val SAME_MONTH_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM")
        private val DEFAULT_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    }

    override fun formatTime(dateTime: LocalDateTime): String {
        return dateTime.format(TIME_FORMATTER.localized())
    }

    override fun formatDataPeriod(dataPeriod: DataPeriod): String {
        return when (dataPeriod.periodType) {
            DataPeriodType.DAY -> dataPeriod.startDate.format(DAY_FORMATTER.localized())
            DataPeriodType.WEEK -> dataPeriod.startDate.format(WEEK_FORMATTER.localized())
            DataPeriodType.MONTH -> {
                buildString {
                    append(dataPeriod.startDate.format(MONTH_NAME_FORMATTER.localized()).take(3))
                    append("\n")
                    append(dataPeriod.startDate.year)
                }.capitalize(Locale.getDefault())
            }
            // Don't use year formatting because it doesn't work correctly sometimes.
            DataPeriodType.YEAR -> dataPeriod.startDate.year.toString()
        }
    }

    override fun formatAsDay(localDate: LocalDate): String {
        // Check if today.
        val today = LocalDate.now()
        val formatter = when (today.year) {
            localDate.year -> {
                if (today.month == localDate.month) {
                    SAME_MONTH_FORMATTER
                } else {
                    DEFAULT_FORMATTER
                }
            }
            else -> {
                DEFAULT_FORMATTER
            }
        }
        return localDate.format(formatter.localized())
    }

    override fun formatForTransaction(dateTime: LocalDateTime): String {
        // Check if today.
        val today = LocalDate.now()
        val formatter = when {
            today == dateTime.toLocalDate() -> {
                TODAY_TIME_FORMATTER
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