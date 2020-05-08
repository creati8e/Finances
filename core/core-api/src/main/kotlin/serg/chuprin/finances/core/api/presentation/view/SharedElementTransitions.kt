package serg.chuprin.finances.core.api.presentation.view

import androidx.fragment.app.Fragment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.transition.MaterialContainerTransform
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.core.api.presentation.view.extensions.getAttributeColor

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
const val SHARED_ELEMENT_TRANSITION_DURATION = 350L

fun Fragment.setEnterSharedElementTransition(setup: (MaterialContainerTransform.() -> Unit)? = null) {
    val context = requireContext()
    val shapeAppearance = ShapeAppearanceModel().withCornerSize(context.dpToPx(16).toFloat())
    sharedElementEnterTransition = MaterialContainerTransform(context).apply {
        startShapeAppearanceModel = shapeAppearance
        duration = SHARED_ELEMENT_TRANSITION_DURATION
        fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        containerColor = context.getAttributeColor(android.R.attr.colorBackground)
        setup?.invoke(this)
    }
}