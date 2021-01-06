package serg.chuprin.finances.feature.userprofile.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.User

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
sealed class UserProfileAction {

    class UpdateUser(
        val user: User
    ) : UserProfileAction()

    class ExecuteIntent(
        val intent: UserProfileIntent
    ) : UserProfileAction()

}