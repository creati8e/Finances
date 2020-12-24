package serg.chuprin.finances.core.categories.shares.presentation.view.adapter.renderer

import android.content.res.ColorStateList
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.cell_dashboard_category.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.extensions.containsType
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.categories.shares.R
import serg.chuprin.finances.core.categories.shares.presentation.model.cell.CategoryChipCell

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class CategoryChipCellRenderer<T : CategoryChipCell>(
    private val onCategoryClicked: (adapterPosition: Int) -> Unit
) : ContainerRenderer<T>() {

    override val type: Int = R.layout.cell_dashboard_category

    override fun bindView(holder: ContainerHolder, model: T) {
        bindData(holder.categoryChip, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: T,
        payloads: MutableList<Any>
    ) {
        if (payloads.containsType<CategoryChipCell.CellChangedPayload>()) {
            bindData(holder.categoryChip, model)
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            categoryChip.onClick {
                onCategoryClicked(adapterPosition)
            }
        }
    }

    private fun bindData(chip: Chip, cell: T) {
        with(chip) {
            text = cell.chipText
            tag = cell.transitionName
            transitionName = cell.transitionName
            chipBackgroundColor = ColorStateList.valueOf(cell.colorInt)
        }
    }

}