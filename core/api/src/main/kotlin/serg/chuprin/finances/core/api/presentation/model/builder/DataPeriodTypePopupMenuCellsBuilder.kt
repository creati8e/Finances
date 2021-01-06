package serg.chuprin.finances.core.api.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.presentation.model.cells.DataPeriodTypePopupMenuCell

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
interface DataPeriodTypePopupMenuCellsBuilder {

    fun build(
        periodTypes: List<DataPeriodType> = DataPeriodType.cachedValues
    ): List<DataPeriodTypePopupMenuCell>

}