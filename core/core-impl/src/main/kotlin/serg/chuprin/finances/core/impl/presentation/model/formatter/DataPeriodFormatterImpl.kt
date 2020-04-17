package serg.chuprin.finances.core.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.core.api.domain.model.DataPeriodType
import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.impl.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DataPeriodFormatterImpl @Inject constructor(
    private val resourceManger: ResourceManger
) : DataPeriodFormatter {

    override fun formatAsCurrentPeriod(dataPeriod: DataPeriod): String {
        return when (dataPeriod.periodType) {
            DataPeriodType.MONTH -> {
                val month = resourceManger.getString(R.string.period_type_month)
                getFormatter().run {
                    "$month (${format(dataPeriod.startDate)} - ${format(dataPeriod.endDate)})"
                }
            }
        }
    }

    private fun getFormatter(): DateFormat {
        return SimpleDateFormat.getDateInstance(SimpleDateFormat.DEFAULT, Locale.getDefault())
    }

}