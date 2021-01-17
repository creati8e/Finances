package serg.chuprin.finances.feature.categories.list.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.view_category.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.makeGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.categories.list.R
import serg.chuprin.finances.feature.categories.list.presentation.model.cell.ChildCategoryCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class ChildCategoryCellRenderer : ContainerRenderer<ChildCategoryCell>() {

    override val type: Int = R.layout.cell_child_category

    override fun bindView(viewHolder: ContainerHolder, cell: ChildCategoryCell) {
        with(viewHolder) {
            transactionColorDot.makeGone()
            expansionArrowImageView.makeGone()
            nameTextView.text = cell.category.name
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            itemView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

}