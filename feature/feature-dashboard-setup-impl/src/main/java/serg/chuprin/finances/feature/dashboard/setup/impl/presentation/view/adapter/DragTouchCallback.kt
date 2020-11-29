package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.view.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sergey Chuprin on 15.08.2019.
 */
class DragTouchCallback(
    private val onCellDropped: () -> Unit,
    private val onCellMoved: (Int, Int) -> Unit
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
) {
    private var toPosition = -1
    private var fromPosition = -1

    override fun isLongPressDragEnabled(): Boolean = true

    override fun isItemViewSwipeEnabled(): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        viewHolder?.itemView?.isActivated = true
    }

    override fun onMoved(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        fromPos: Int,
        target: RecyclerView.ViewHolder,
        toPos: Int,
        x: Int,
        y: Int
    ) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
        if (fromPosition != -1 && toPosition != -1) {
            onCellMoved(fromPosition, toPosition)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        fromPosition = viewHolder.adapterPosition
        if (fromPosition > -1) {
            if (toPosition != target.adapterPosition) {
                toPosition = target.adapterPosition
                return true
            }
        }
        return false
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.isActivated = false
        if (fromPosition != -1 && toPosition != -1) {
            onCellDropped()
        }
        toPosition = -1
        fromPosition = -1
    }

}