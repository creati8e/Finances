package serg.chuprin.finances.core.api.presentation.formatter

import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
interface DateFormatter {

    fun formatForTransaction(dateTime: LocalDateTime): String

}