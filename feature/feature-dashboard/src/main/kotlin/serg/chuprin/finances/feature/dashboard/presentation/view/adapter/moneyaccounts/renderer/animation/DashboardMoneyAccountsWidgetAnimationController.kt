package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.renderer.animation

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import serg.chuprin.finances.core.api.presentation.view.extensions.makeGone
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisible

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class DashboardMoneyAccountsWidgetAnimationController {

    private companion object {
        private const val EXPANSION_ARROW_ANIMATION_DURATION = 400L
        private val animationInterpolator = FastOutSlowInInterpolator()
    }

    fun toggleExpansion(
        isExpanded: Boolean,
        expandableLayout: ViewGroup,
        expansionArrowImageView: ImageView
    ) {
        animateExpansionArrow(expansionArrowImageView, isExpanded)

        expandableLayout.animation?.cancel()
        if (isExpanded) {
            collapse(expandableLayout)
        } else {
            expand(expandableLayout)
        }
    }

    private fun expand(expandableLayout: ViewGroup) {
        animate(expandableLayout) { view, interpolatedTime, viewHeight ->
            if (interpolatedTime == 1f) {
                view.makeGone()
            } else {
                with(view) {
                    alpha = 1f - interpolatedTime
                    layoutParams.height = viewHeight - (viewHeight * interpolatedTime).toInt()
                    requestLayout()
                }
            }
        }
    }

    private fun collapse(expandableLayout: ViewGroup) {
        with(expandableLayout) {
            // Measure first to get measured height.
            measure(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.height = 1
            makeVisible()
        }
        animate(expandableLayout) { view, interpolatedTime, viewHeight ->
            with(view) {
                alpha = interpolatedTime
                layoutParams.height = ((viewHeight * interpolatedTime).toInt())
                requestLayout()
            }
        }
    }

    private fun animateExpansionArrow(expansionArrowImageView: ImageView, isExpanded: Boolean) {
        with(expansionArrowImageView) {
            animation?.cancel()
            animate()
                .setInterpolator(animationInterpolator)
                .rotationBy(if (isExpanded) -180f else 180f)
                .setDuration(EXPANSION_ARROW_ANIMATION_DURATION)
                .start()
        }
    }

    private inline fun animate(
        view: View,
        crossinline block: (view: View, interpolatedTime: Float, viewHeight: Int) -> Unit
    ) {
        with(view) {
            // Remember initial view height before changing it.
            val viewHeight = measuredHeight
            startAnimation(
                object : Animation() {
                    override fun applyTransformation(
                        interpolatedTime: Float,
                        transformation: Transformation
                    ) {
                        block(view, interpolatedTime, viewHeight)
                    }
                }.apply<Animation> {
                    interpolator = animationInterpolator

                    val density = context.resources.displayMetrics.density
                    duration = (viewHeight / density).toLong() + 150
                }
            )
        }
    }

}