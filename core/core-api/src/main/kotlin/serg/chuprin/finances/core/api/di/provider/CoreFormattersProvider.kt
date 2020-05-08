package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.formatter.TransactionCategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
interface CoreFormattersProvider {

    val amountFormatter: AmountFormatter

    val dateTimeFormatter: DateTimeFormatter

    val dataPeriodFormatter: DataPeriodFormatter

    val transactionCategoryWithParentFormatter: TransactionCategoryWithParentFormatter

}