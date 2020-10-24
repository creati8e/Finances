package serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_user_profile_image.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.loadImage
import serg.chuprin.finances.feature.userprofile.R
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileImageCell

/**
 * Created by Sergey Chuprin on 25.07.2020.
 */
class UserProfileImageCellRenderer : ContainerRenderer<UserProfileImageCell>() {

    override val type: Int = R.layout.cell_user_profile_image

    override fun bindView(holder: ContainerHolder, model: UserProfileImageCell) {
        with(holder) {
            emailTextView.text = model.email
            usernameTextView.text = model.username
            userPhotoImageView.loadImage(model.imageUrl)
        }
    }

}