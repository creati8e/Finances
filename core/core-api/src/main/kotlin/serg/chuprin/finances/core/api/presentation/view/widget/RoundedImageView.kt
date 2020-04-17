package serg.chuprin.finances.core.api.presentation.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.view.extensions.drawable

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        clipToOutline = true
        outlineProvider = ViewOutlineProvider.BACKGROUND
        background = context.drawable(R.drawable.bg_selection_rounded)
    }

}