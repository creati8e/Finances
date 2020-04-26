package serg.chuprin.finances.core.piechart

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
internal fun Context.dpToPx(dpValue: Int): Float = dpValue * resources.displayMetrics.density

internal fun Context.spToPx(sp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)
}

internal fun Context.getAttributeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}