package serg.chuprin.finances.core.api.presentation.formatter

import serg.chuprin.finances.core.api.domain.model.DataPeriod

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
interface DataPeriodFormatter {

    fun formatAsCurrentPeriod(dataPeriod: DataPeriod): String

}