package serg.chuprin.finances.core.currency.choice.api.presentation.view.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sergey Chuprin on 09.04.2020.
 */
class FadingEdgeRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    override fun isPaddingOffsetRequired(): Boolean = !clipToPadding

    override fun getTopPaddingOffset(): Int = if (clipToPadding) 0 else -paddingTop

    override fun getLeftPaddingOffset(): Int = if (clipToPadding) 0 else -paddingLeft

    override fun getRightPaddingOffset(): Int = if (clipToPadding) 0 else paddingRight

    override fun getBottomPaddingOffset(): Int = if (clipToPadding) 0 else paddingBottom

}