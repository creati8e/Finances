package serg.chuprin.finances.feature.userprofile.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.feature.userprofile.presentation.model.UserInfo

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
data class UserProfileState(
    val userInfo: UserInfo = UserInfo(Id.UNKNOWN, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
)