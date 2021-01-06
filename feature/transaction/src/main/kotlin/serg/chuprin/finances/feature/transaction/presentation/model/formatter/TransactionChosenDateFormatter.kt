package serg.chuprin.finances.feature.transaction.presentation.model.formatter

import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.transaction.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
class TransactionChosenDateFormatter @Inject constructor(
    private val resourceManger: ResourceManger
) {

    fun format(date: LocalDate): String {
        val today = LocalDate.now()
        if (date == today) {
            return resourceManger.getString(R.string.transaction_date_today)
        }
        if (date == today.minusDays(1)) {
            return resourceManger.getString(R.string.transaction_date_yesterday)
        }

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date)
    }

}