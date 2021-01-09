package serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_user_profile_image.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.loadImage
import serg.chuprin.finances.feature.userprofile.R
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileImageCell
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.diff.UserProfileChangedDiffPayload

/**
 * Created by Sergey Chuprin on 25.07.2020.
 */
class UserProfileImageCellRenderer : ContainerRenderer<UserProfileImageCell>() {

    override val type: Int = R.layout.cell_user_profile_image

    override fun bindView(viewHolder: ContainerHolder, cell: UserProfileImageCell) {
        bind(viewHolder, cell)
    }

    override fun bindView(
        viewHolder: ContainerHolder,
        cell: UserProfileImageCell,
        payloads: MutableList<Any>
    ) {
        if (payloads.any { it is UserProfileChangedDiffPayload }) {
            bind(viewHolder, cell)
        }
    }

    private fun bind(
        viewHolder: ContainerHolder,
        cell: UserProfileImageCell
    ) {
        with(viewHolder) {
            emailTextView.text = cell.email
            usernameTextView.text = cell.username
            userPhotoImageView.loadImage(cell.imageUrl)
        }
    }

}