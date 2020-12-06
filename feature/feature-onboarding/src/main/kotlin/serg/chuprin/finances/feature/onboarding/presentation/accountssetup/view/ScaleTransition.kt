package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.view

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues


/**
 * Created by Sergey Chuprin on 06.12.2020.
 */
class ScaleTransition : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (endValues == null || startValues == null) {
            return null // no values
        }
        val startX = startValues.values[PROPNAME_SCALE_X] as Float
        val startY = startValues.values[PROPNAME_SCALE_Y] as Float
        val endX = endValues.values[PROPNAME_SCALE_X] as Float
        val endY = endValues.values[PROPNAME_SCALE_Y] as Float
        if (startX == endX && startY == endY) return null // no scale to run
        val view: View = startValues.view
        val propX = PropertyValuesHolder.ofFloat(PROPNAME_SCALE_X, startX, endX)
        val propY = PropertyValuesHolder.ofFloat(PROPNAME_SCALE_Y, startY, endY)
        val valAnim = ValueAnimator.ofPropertyValuesHolder(propX, propY)
        valAnim.addUpdateListener { valueAnimator ->
            view.pivotX = view.width / 2f
            view.pivotY = view.height / 2f
            view.scaleX = valueAnimator.getAnimatedValue(PROPNAME_SCALE_X) as Float
            view.scaleY = valueAnimator.getAnimatedValue(PROPNAME_SCALE_Y) as Float
        }
        return valAnim
    }

    private fun captureValues(values: TransitionValues) {
        values.values[PROPNAME_SCALE_X] = values.view.scaleX
        values.values[PROPNAME_SCALE_Y] = values.view.scaleY
    }

    companion object {
        private const val PROPNAME_SCALE_X = "PROPNAME_SCALE_X"
        private const val PROPNAME_SCALE_Y = "PROPNAME_SCALE_Y"
    }

}