package serg.chuprin.finances.core.api.presentation.model.cells

import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class DataPeriodTypePopupMenuCell(
    override val title: String,
    override val isChecked: Boolean,
    override val isCheckable: Boolean,
    val periodType: DataPeriodType
) : PopupMenuCell