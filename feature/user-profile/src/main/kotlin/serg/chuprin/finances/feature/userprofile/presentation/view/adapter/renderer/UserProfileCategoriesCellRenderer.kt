package serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer

import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.userprofile.R
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileCategoriesCell

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class UserProfileCategoriesCellRenderer : ContainerRenderer<UserProfileCategoriesCell>() {

    override val type: Int = R.layout.cell_user_profile_categories

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