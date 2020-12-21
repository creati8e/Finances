package serg.chuprin.finances.feature.transactions.presentation.view.widget

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntRange
import serg.chuprin.finances.core.api.presentation.view.extensions.drawable
import serg.chuprin.finances.feature.transactions.R

/**
 * Created by Sergey Chuprin on 21.12.2020.
 */
class ChartBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        isClickable = true
        isFocusable = true
        background = context.drawable(R.drawable.bg_chart)
        if (isInEditMode) {
            setProgress(50)
        }
    }

    fun setProgress(@IntRange(from = 0, to = 100) progress: Int) {
        val background = background as LayerDrawable
        background.findDrawableByLayerId(R.id.trackDrawable).level = progress * 100
    }

}