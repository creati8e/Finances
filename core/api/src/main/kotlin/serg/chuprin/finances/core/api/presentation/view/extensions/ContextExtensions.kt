package serg.chuprin.finances.core.api.presentation.view.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
// region Dimens conversion

fun Context.dpToPx(dpValue: Int): Int = (dpValue * resources.displayMetrics.density).toInt()

/**
 * Pass desirable pixel value to obtain its density-independent representation,
 * e. g.: context.pixToDip(360) means translate 360px to corresponding amount of dp.
 * The result can be used, for instance, to compare view size with screen width or height.
 */
fun Context.pxToDp(pixValue: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixValue, resources.displayMetrics)
}

// endregion

fun Context.getDimenDpFloat(@DimenRes dimenResId: Int): Float {
    return resources.getDimension(dimenResId).let(::pxToDp)
}

fun Context.getDimenDpInt(@DimenRes dimenResId: Int): Int = getDimenDpFloat(dimenResId).toInt()

@ColorInt
fun Context.getColorInt(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.drawable(@DrawableRes drawableRes: Int): Drawable {
    return ContextCompat.getDrawable(this, drawableRes)!!
}

fun Context.getAttributeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}