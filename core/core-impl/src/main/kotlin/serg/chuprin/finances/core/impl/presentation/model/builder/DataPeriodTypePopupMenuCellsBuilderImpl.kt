package serg.chuprin.finances.core.impl.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilder
import serg.chuprin.finances.core.api.presentation.model.cells.DataPeriodTypePopupMenuCell
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
internal class DataPeriodTypePopupMenuCellsBuilderImpl @Inject constructor(
    private val resourceManger: ResourceManger
) : DataPeriodTypePopupMenuCellsBuilder {

    override fun build(periodTypes: List<DataPeriodType>): List<DataPeriodTypePopupMenuCell> {
        return periodTypes.map { periodType ->
            val titleStringRes = when (periodType) {
                DataPeriodType.DAY -> CoreR.string.period_type_day
                DataPeriodType.WEEK -> CoreR.string.period_type_week
                DataPeriodType.YEAR -> CoreR.string.period_type_year
                DataPeriodType.MONTH -> CoreR.string.period_type_month
            }
            DataPeriodTypePopupMenuCell(
                isChecked = false,
                isCheckable = false,
                periodType = periodType,
                title = resourceManger.getString(titleStringRes)
            )
        }
    }

}