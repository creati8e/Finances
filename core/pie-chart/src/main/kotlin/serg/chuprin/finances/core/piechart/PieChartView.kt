package serg.chuprin.finances.core.piechart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import serg.chuprin.finances.core.piechart.model.PieChartData
import serg.chuprin.finances.core.piechart.model.PieChartDataPart
import serg.chuprin.finances.core.piechart.model.PieChartRenderData


/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private companion object {

        // region Text size.

        private const val PRIMARY_TEXT_SIZE_SP = 20f
        private const val SECONDARY_TEXT_SIZE_SP = 14f

        // endregion

        private const val PORTION_SIZE_DP = 15
        private const val ANIMATION_DURATION = 1000L
        private const val DISTANCE_BETWEEN_PARTS = 4f

        private val ANIMATION_INTERPOLATOR = FastOutSlowInInterpolator()
    }

    private var renderData = listOf<PieChartRenderData>()
    private var progressRenderData = listOf<PieChartRenderData>()

    private var chartRadius = 0f
    private var primaryText: String = ""
    private var secondaryText: String = ""

    private val chartArea = RectF()
    private val partSize = context.dpToPx(PORTION_SIZE_DP)

    private val centerY: Float
        get() = chartArea.centerY()

    private val dataPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = partSize
    }

    private val primaryTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = if (isInEditMode) {
            Color.BLACK
        } else {
            context.getAttributeColor(android.R.attr.textColorPrimary)
        }
        textSize = context.spToPx(PRIMARY_TEXT_SIZE_SP)
    }

    private val secondaryTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = if (isInEditMode) {
            Color.BLACK
        } else {
            context.getAttributeColor(android.R.attr.textColorPrimary)
        }
        textSize = context.spToPx(SECONDARY_TEXT_SIZE_SP)
    }

    init {
        if (isInEditMode) {
            setData(
                PieChartPreviewData.data,
                animate = false,
                primaryText = "1 000 000 $",
                secondaryText = "8 categories"
            )
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val minDimension = minOf(measuredWidth, measuredHeight)
        chartRadius = minDimension / 2f - paddingStart - paddingEnd - partSize * 2

        val fl = partSize / 2
        chartArea.set(
            0f + fl + paddingStart,
            0f + fl + paddingTop,
            minDimension - fl - paddingEnd,
            minDimension - fl - paddingEnd
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawText(
            primaryText,
            getTextCenterX(primaryText, primaryTextPaint),
            centerY,
            primaryTextPaint
        )

        canvas.drawText(
            secondaryText,
            getTextCenterX(secondaryText, secondaryTextPaint),
            centerY + primaryTextPaint.textSize,
            secondaryTextPaint
        )

        // Draw pie itself.
        renderData.forEachIndexed { index, renderData ->
            dataPaint.color = renderData.color
            canvas.drawArc(
                chartArea,
                progressRenderData[index].startAngle,
                progressRenderData[index].sweepAngle,
                false,
                dataPaint
            )
        }
    }

    fun setData(
        pieChartData: PieChartData,
        primaryText: String,
        secondaryText: String,
        animate: Boolean
    ) {
        this.primaryText = primaryText
        this.secondaryText = secondaryText

        renderData = buildRenderData(pieChartData.parts, pieChartData.maxValue)
        if (animate) {
            PieChartAnimation(renderData) { animatedValues ->
                progressRenderData = animatedValues
                requestLayout()
            }.run {
                duration = ANIMATION_DURATION
                interpolator = ANIMATION_INTERPOLATOR
                startAnimation(this)
            }
        } else {
            progressRenderData = renderData
            invalidate()
        }
    }

    private fun buildRenderData(
        list: List<PieChartDataPart>,
        maxValue: Float
    ): List<PieChartRenderData> {
        // If only single portion is displayed, remove the gap.
        if (list.size == 1) {
            val piePortion = list.first()
            return listOf(
                PieChartRenderData(
                    startAngle = -90f,
                    color = piePortion.colorInt,
                    sweepAngle = (piePortion.value * 360 / maxValue).coerceAtLeast(2f)
                )
            )
        }
        @Suppress("RemoveExplicitTypeArguments")
        return buildList<PieChartRenderData> {
            val distanceBetweenParts = DISTANCE_BETWEEN_PARTS
            list.sortedByDescending(PieChartDataPart::value).forEach { piePortion ->

                val startAngle = if (isEmpty()) {
                    -90f
                } else {
                    val last = last()
                    last.startAngle + last.sweepAngle + distanceBetweenParts
                }
                // Ensure that displayed portions is not very small.
                val sweepAngle =
                    (piePortion.value * 360 / maxValue - distanceBetweenParts).coerceAtLeast(2f)

                add(
                    PieChartRenderData(
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        color = piePortion.colorInt
                    )
                )
            }
        }
    }

    private fun getTextCenterX(text: String, paint: Paint): Float {
        val textWidth = paint.measureText(text)
        return (measuredWidth + partSize - textWidth) / 2f - paint.textSize / 2f
    }

}