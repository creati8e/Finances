package serg.chuprin.finances.core.api.presentation.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updatePadding
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone

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

    val imageView: ImageView
    private val titleTextView: TextView
    private val contentTextView: TextView

    init {
        val view = View.inflate(context, R.layout.view_zero_data, this)

        imageView = view.findViewById(R.id.iconImageView)
        titleTextView = view.findViewById(R.id.primaryTextView)
        contentTextView = view.findViewById(R.id.contentTextView)
        updatePadding(top = context.dpToPx(24), bottom = context.dpToPx(24))
    }

    fun setup(
        @DrawableRes iconRes: Int?,
        @StringRes titleRes: Int,
        @StringRes contentMessageRes: Int?
    ) {

        titleTextView.setText(titleRes)

        iconRes?.let(imageView::setImageResource)
        imageView.makeVisibleOrGone(iconRes != null)

        contentMessageRes?.let(contentTextView::setText)
        contentTextView.makeVisibleOrGone(contentMessageRes != null)
    }

}