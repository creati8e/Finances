package serg.chuprin.finances.core.api.presentation.view.extensions

import android.content.Context
import androidx.annotation.ColorInt

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
@ColorInt
fun Context.getBackgroundColor(): Int {
    return getAttributeColor(android.R.attr.colorBackground)
}

@ColorInt
fun Context.getPrimaryTextColor(): Int {
    return getAttributeColor(android.R.attr.textColorPrimary)
}