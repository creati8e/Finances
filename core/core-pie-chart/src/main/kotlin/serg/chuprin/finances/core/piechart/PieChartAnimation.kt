package serg.chuprin.finances.core.piechart

import android.view.animation.Animation
import android.view.animation.Transformation
import serg.chuprin.finances.core.piechart.model.PieChartRenderData

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
internal class PieChartAnimation(
    private val chartRenderData: List<PieChartRenderData>,
    private val onAnimationUpdate: (List<PieChartRenderData>) -> Unit
) : Animation() {

    /**
     * All portions are animated from the same start angle.
     */
    private val progressRenderData = chartRenderData.map { -90f to 0f }

    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
        val animatedValues = chartRenderData
            .zip(progressRenderData) { data, (startAngle, sweepAngle) ->
                val progressSweepAngle = (data.sweepAngle - sweepAngle) * interpolatedTime
                val progressStartAngle = -90 + (data.startAngle - startAngle) * interpolatedTime
                data.copy(startAngle = progressStartAngle, sweepAngle = progressSweepAngle)
            }
        onAnimationUpdate(animatedValues)
    }

}