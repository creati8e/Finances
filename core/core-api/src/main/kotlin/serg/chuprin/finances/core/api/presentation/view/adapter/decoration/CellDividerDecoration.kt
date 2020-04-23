package serg.chuprin.finances.core.api.presentation.view.adapter.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import androidx.annotation.DrawableRes
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.view.adapter.DividerAdapter
import serg.chuprin.finances.core.api.presentation.view.extensions.alphaInt
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.core.api.presentation.view.extensions.drawable
import kotlin.math.roundToInt

/**
 * Created by Sergey Chuprin on 09.08.2019.
 */
class CellDividerDecoration(
    context: Context,
    private val dividerAdapter: DividerAdapter,
    @DrawableRes dividerRes: Int = R.drawable.bg_list_cell_divider,
    marginStartDp: Int = 16,
    marginEndDp: Int = 16
) : RecyclerView.ItemDecoration() {

    private val bounds = Rect()
    private val divider = context.drawable(dividerRes)
    private val marginEndPx = context.dpToPx(marginEndDp)
    private val marginStartPx = context.dpToPx(marginStartDp)

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.forEach { child ->
            val childAdapterPosition = parent.getChildAdapterPosition(child)

            if (childAdapterPosition != RecyclerView.NO_POSITION
                && dividerAdapter.shouldDrawDividerForCellAt(childAdapterPosition)
            ) {
                parent.getDecoratedBoundsWithMargins(child, bounds)

                val bottom = bounds.bottom + child.translationY.roundToInt()
                val top = bottom - divider.intrinsicHeight

                with(divider) {
                    setBounds(child.left + marginStartPx, top, child.right - marginEndPx, bottom)
                    alpha = child.alphaInt
                    draw(canvas)
                }
            }
        }
    }

}