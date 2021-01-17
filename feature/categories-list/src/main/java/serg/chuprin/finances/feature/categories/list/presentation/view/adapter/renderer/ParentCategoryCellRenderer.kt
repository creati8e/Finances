package serg.chuprin.finances.feature.categories.list.presentation.view.adapter.renderer

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.synthetic.main.view_category.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.extensions.containsType
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.categories.list.R
import serg.chuprin.finances.feature.categories.list.presentation.model.cell.ParentCategoryCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class ParentCategoryCellRenderer : ContainerRenderer<ParentCategoryCell>() {

    private companion object {
        private const val EXPANSION_ARROW_ANIMATION_DURATION = 400L
        private val animationInterpolator = FastOutSlowInInterpolator()
    }


    override val type: Int = R.layout.cell_parent_category

    override fun bindView(viewHolder: ContainerHolder, cell: ParentCategoryCell) {
        with(viewHolder) {
            nameTextView.text = cell.category.name
            transactionColorDot.imageTintList = ColorStateList.valueOf(cell.color)

            expansionArrowImageView.makeVisibleOrGone(cell.isExpansionAvailable)
            if (cell.isExpansionAvailable) {
                expansionArrowImageView.rotation = if (cell.isExpanded) -180f else 0f
            }
        }
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: ParentCategoryCell,
        payloads: MutableList<Any>
    ) {
        if (payloads.containsType<ParentCategoryCell.ExpansionChangedPayload>()) {
            animateExpansionArrow(viewHolder.expansionArrowImageView, cell.isExpanded)
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            expansionArrowImageView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
            itemView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

    private fun animateExpansionArrow(expansionArrowImageView: ImageView, isExpanded: Boolean) {
        with(expansionArrowImageView) {
            animation?.cancel()
            animate()
                .setInterpolator(animationInterpolator)
                .rotation(if (isExpanded) -180f else 0f)
                .setDuration(EXPANSION_ARROW_ANIMATION_DURATION)
                .start()
        }
    }

}