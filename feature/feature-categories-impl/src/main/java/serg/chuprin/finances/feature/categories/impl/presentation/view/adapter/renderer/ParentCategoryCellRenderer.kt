package serg.chuprin.finances.feature.categories.impl.presentation.view.adapter.renderer

import android.content.res.ColorStateList
import kotlinx.android.synthetic.main.view_category.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisible
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.categories.impl.R
import serg.chuprin.finances.feature.categories.impl.presentation.model.cell.ParentCategoryCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class ParentCategoryCellRenderer : ContainerRenderer<ParentCategoryCell>() {

    override val type: Int = R.layout.cell_parent_category

    override fun bindView(holder: ContainerHolder, model: ParentCategoryCell) {
        with(holder) {
            expansionArrowImageView.makeVisible()
            nameTextView.text = model.category.name
            transactionColorDot.imageTintList = ColorStateList.valueOf(model.color)
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            expansionArrowImageView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

}