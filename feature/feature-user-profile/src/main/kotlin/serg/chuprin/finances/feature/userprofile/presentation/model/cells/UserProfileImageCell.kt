package serg.chuprin.finances.feature.userprofile.presentation.model.cells

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 25.07.2020.
 */
data class UserProfileImageCell(
    val imageUrl: String,
    val username: String,
    val email: String
) : BaseCell