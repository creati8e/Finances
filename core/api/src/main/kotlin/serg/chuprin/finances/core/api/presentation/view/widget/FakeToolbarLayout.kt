package serg.chuprin.finances.core.api.presentation.view.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.FrameLayout
import com.google.android.material.shape.MaterialShapeDrawable
import serg.chuprin.finances.core.api.presentation.view.extensions.getAttributeColor

/**
 * Created by Sergey Chuprin on 09.05.2020.
 */
class FakeToolbarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val materialShapeDrawable = MaterialShapeDrawable.createWithElevationOverlay(context)

    init {
        materialShapeDrawable.fillColor = ColorStateList.valueOf(
            context.getAttributeColor(android.R.attr.colorBackground)
        )
        background = materialShapeDrawable
    }

    override fun setElevation(elevation: Float) {
        super.setElevation(elevation)
        materialShapeDrawable.elevation = elevation
    }

}