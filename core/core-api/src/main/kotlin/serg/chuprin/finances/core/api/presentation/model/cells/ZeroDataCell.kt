package serg.chuprin.finances.core.api.presentation.model.cells

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
data class ZeroDataCell(
    @DrawableRes val iconRes: Int?,
    @StringRes val titleRes: Int,
    @StringRes val contentMessageRes: Int?,
    val fillParent: Boolean = true
) : BaseCell