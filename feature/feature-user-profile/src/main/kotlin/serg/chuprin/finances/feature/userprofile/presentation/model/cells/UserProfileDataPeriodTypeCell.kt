package serg.chuprin.finances.feature.userprofile.presentation.model.cells

import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
data class UserProfileDataPeriodTypeCell(
    val periodTypeDisplayName: String,
    val dataPeriodType: DataPeriodType
) : BaseCell