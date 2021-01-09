package serg.chuprin.finances.core.categories.shares.presentation.view.adapter.renderer

import android.content.res.ColorStateList
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.cell_dashboard_category.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.extensions.containsType
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.categories.shares.R
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategoryShareCell

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class CategoryShareCellRenderer<T : CategoryShareCell>(
    private val onCategoryClicked: (adapterPosition: Int) -> Unit
) : ContainerRenderer<T>() {

    override val type: Int = R.layout.cell_dashboard_category

    override fun bindView(viewHolder: ContainerHolder, cell: T) {
        bindData(viewHolder.categoryChip, cell)
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: T,
        payloads: MutableList<Any>
    ) {
        if (payloads.containsType<CategoryShareCell.CellChangedPayload>()) {
            bindData(viewHolder.categoryChip, cell)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            categoryChip.onClick {
                onCategoryClicked(adapterPosition)
            }
        }
    }

    private fun bindData(chip: Chip, cell: T) {
        with(chip) {
            text = cell.text
            tag = cell.transitionName
            transitionName = cell.transitionName
            chipBackgroundColor = ColorStateList.valueOf(cell.colorInt)
        }
    }

}