package serg.chuprin.finances.core.api.presentation.formatter

import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
interface DataPeriodFormatter {

    fun formatType(dataPeriodType: DataPeriodType): String

    fun formatAsCurrentPeriod(dataPeriod: DataPeriod): String

}