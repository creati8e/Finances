package serg.chuprin.finances.core.api.presentation.view

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.core.api.presentation.view.extensions.getAttributeColor

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
const val SHARED_ELEMENT_TRANSITION_DURATION = 400L

fun Fragment.setSharedElementTransitions(
    enterTransitionSetup: (MaterialContainerTransform.() -> Unit)? = null,
    returnTransitionSetup: (MaterialContainerTransform.() -> Unit)? = null,
) {
    val context = requireContext()
    sharedElementEnterTransition = context.buildSharedElementTransition(enterTransitionSetup)
    sharedElementReturnTransition = context.buildSharedElementTransition(returnTransitionSetup)
    setExitSharedElementTransition()
}

fun Fragment.setExitSharedElementTransition() {
    exitTransition = MaterialElevationScale(/* growing= */ false).apply {
        duration = SHARED_ELEMENT_TRANSITION_DURATION
    }
    reenterTransition = MaterialElevationScale(/* growing= */ true).apply {
        duration = SHARED_ELEMENT_TRANSITION_DURATION
    }
}

private fun Context.buildSharedElementTransition(
    setup: (MaterialContainerTransform.() -> Unit)?
): MaterialContainerTransform {
    val shapeAppearance = ShapeAppearanceModel().withCornerSize(dpToPx(16).toFloat())
    return MaterialContainerTransform().apply {
        startShapeAppearanceModel = shapeAppearance
        duration = SHARED_ELEMENT_TRANSITION_DURATION
        fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        containerColor = getAttributeColor(android.R.attr.colorBackground)
        setup?.invoke(this)
    }
}