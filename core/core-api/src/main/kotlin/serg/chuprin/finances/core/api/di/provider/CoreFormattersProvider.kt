package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
interface CoreFormattersProvider {

    val amountFormatter: AmountFormatter

    val dataPeriodFormatter: DataPeriodFormatter

}