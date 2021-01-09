package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.presentation.formatter.*

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
interface CoreFormattersProvider {

    val amountFormatter: AmountFormatter

    val dateTimeFormatter: DateTimeFormatter

    val dataPeriodFormatter: DataPeriodFormatter

    val categoryColorFormatter: CategoryColorFormatter

    val categoryWithParentFormatter: CategoryWithParentFormatter

}