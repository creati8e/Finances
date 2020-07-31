package serg.chuprin.finances.feature.userprofile.presentation.model

import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
data class UserInfo(
    val id: Id,
    val email: String,
    val photoUrl: String,
    val displayName: String
)