package serg.chuprin.finances.core.api.presentation.view.widget

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.Px

/**
 * Created by Sergey Chuprin on 12.01.2021.
 */
class TextDrawable(
    private val text: String,
    @ColorInt
    private val textColor: Int,
    @Px
    private val textSize: Float
) : Drawable() {

    private val textPaint = Paint().apply {
        isAntiAlias = true
        isFakeBoldText = true
        color = this@TextDrawable.textColor
        textSize = this@TextDrawable.textSize
    }

    @SuppressLint("CanvasSize")
    override fun draw(canvas: Canvas) {
        canvas.drawText(text, 0f, canvas.height / 2f - textPaint.textSize / 2f, textPaint)
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        textPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

}