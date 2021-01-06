package serg.chuprin.finances.core.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.impl.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
internal class DataPeriodFormatterImpl @Inject constructor(
    private val resourceManger: ResourceManger
) : DataPeriodFormatter {

    private companion object {
        private val CURRENT_PERIOD_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    }

    override fun formatType(dataPeriodType: DataPeriodType): String {
        return formatDataPeriodType(dataPeriodType)
    }

    override fun formatAsCurrentPeriod(dataPeriod: DataPeriod): String {
        val periodTypeStringRes = formatDataPeriodType(dataPeriod.periodType)
        return "$periodTypeStringRes (${dataPeriod.startDate.format()} - ${dataPeriod.endDate.format()})"
    }

    private fun formatDataPeriodType(dataPeriodType: DataPeriodType): String {
        val stringRes = when (dataPeriodType) {
            DataPeriodType.DAY -> R.string.period_type_day
            DataPeriodType.WEEK -> R.string.period_type_week
            DataPeriodType.YEAR -> R.string.period_type_year
            DataPeriodType.MONTH -> R.string.period_type_month
        }
        return resourceManger.getString(stringRes)
    }

    private fun LocalDateTime.format(): String = format(CURRENT_PERIOD_FORMATTER.localized())

}