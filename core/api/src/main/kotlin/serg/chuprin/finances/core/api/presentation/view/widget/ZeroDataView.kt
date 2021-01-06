package serg.chuprin.finances.core.api.presentation.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updatePadding
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick

/**
 * Created by Sergey Chuprin on 10.04.2020.
 */
class ZeroDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(
    context,
    attrs,
    defStyleAttr
) {

    val button: Button
    private val imageView: ImageView
    private val titleTextView: TextView
    private val contentTextView: TextView

    init {
        val view = View.inflate(context, R.layout.view_zero_data, this)

        button = view.findViewById(R.id.button)
        imageView = view.findViewById(R.id.iconImageView)
        titleTextView = view.findViewById(R.id.primaryTextView)
        contentTextView = view.findViewById(R.id.contentTextView)
        updatePadding(top = context.dpToPx(24), bottom = context.dpToPx(24))
    }

    fun setup(
        @DrawableRes iconRes: Int?,
        @StringRes titleRes: Int,
        @StringRes contentMessageRes: Int?,
        @StringRes buttonRes: Int?
    ) {

        titleTextView.setText(titleRes)

        buttonRes?.let(button::setText)
        button.makeVisibleOrGone(buttonRes != null)

        iconRes?.let(imageView::setImageResource)
        imageView.makeVisibleOrGone(iconRes != null)

        contentMessageRes?.let(contentTextView::setText)
        contentTextView.makeVisibleOrGone(contentMessageRes != null)
    }

    fun setOnButtonClickListener(listener: () -> Unit) = button.onClick(listener)

}