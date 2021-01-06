package serg.chuprin.finances.core.api.presentation.view.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 31.01.2019.
 */
open class DiffCallback<T : BaseCell> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldCell: T, newCell: T): Boolean {
        return when {
            oldCell.javaClass != newCell.javaClass -> false
            oldCell is DiffCell<*> && newCell is DiffCell<*> -> {
                oldCell.diffCellId == newCell.diffCellId
            }
            else -> oldCell.hashCode() == newCell.hashCode()
        }
    }

    override fun areContentsTheSame(oldCell: T, newCell: T): Boolean {
        if (oldCell is DiffCell<*> && newCell is DiffCell<*>) {
            @Suppress("ReplaceCallWithBinaryOperator")
            return oldCell.equals(newCell)
        }
        return false
    }

    @Suppress("RedundantOverride")
    override fun getChangePayload(oldCell: T, newCell: T): Any? {
        return super.getChangePayload(oldCell, newCell)
    }

}