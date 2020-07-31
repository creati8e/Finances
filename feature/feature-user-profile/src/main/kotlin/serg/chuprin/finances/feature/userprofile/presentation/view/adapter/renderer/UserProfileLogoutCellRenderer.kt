package serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer

import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.feature.userprofile.R
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileLogoutCell

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
class UserProfileLogoutCellRenderer : ContainerRenderer<UserProfileLogoutCell>() {
    override val type: Int = R.layout.cell_user_profile_logout
}