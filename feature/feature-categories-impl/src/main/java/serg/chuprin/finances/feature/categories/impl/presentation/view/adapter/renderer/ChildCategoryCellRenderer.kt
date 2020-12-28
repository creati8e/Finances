package serg.chuprin.finances.feature.categories.impl.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.view_category.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.makeGone
import serg.chuprin.finances.feature.categories.impl.R
import serg.chuprin.finances.feature.categories.impl.presentation.model.cell.ChildCategoryCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class ChildCategoryCellRenderer : ContainerRenderer<ChildCategoryCell>() {

    override val type: Int = R.layout.cell_child_category

    override fun bindView(holder: ContainerHolder, model: ChildCategoryCell) {
        with(holder) {
            transactionColorDot.makeGone()
            expansionArrowImageView.makeGone()
            nameTextView.text = model.category.name
        }
    }

}