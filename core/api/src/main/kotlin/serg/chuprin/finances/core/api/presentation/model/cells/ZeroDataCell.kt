package serg.chuprin.finances.core.api.presentation.model.cells

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
data class ZeroDataCell(
    @DrawableRes val iconRes: Int?,
    @StringRes val titleRes: Int,
    @StringRes val contentMessageRes: Int?,
    @StringRes val buttonRes: Int? = null,
    val buttonTransitionName: String = EMPTY_STRING,
    val fillParent: Boolean = true
) : BaseCell