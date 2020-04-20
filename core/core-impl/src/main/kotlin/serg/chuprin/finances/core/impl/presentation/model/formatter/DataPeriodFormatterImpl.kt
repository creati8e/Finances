package serg.chuprin.finances.core.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.core.api.domain.model.DataPeriodType
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

    override fun formatAsCurrentPeriod(dataPeriod: DataPeriod): String {
        return when (dataPeriod.periodType) {
            DataPeriodType.MONTH -> {
                val month = resourceManger.getString(R.string.period_type_month)
                "$month (${dataPeriod.startDate.format()} - ${dataPeriod.endDate.format()})"
            }
        }
    }

    private fun LocalDateTime.format(): String = format(CURRENT_PERIOD_FORMATTER.localized())

}