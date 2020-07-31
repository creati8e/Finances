package serg.chuprin.finances.feature.userprofile.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
class UserProfileBootstrapper @Inject constructor(
    private val userRepository: UserRepository
) : StoreBootstrapper<UserProfileAction> {

    override fun invoke(): Flow<UserProfileAction> {
        return userRepository
            .currentUserSingleFlow()
            .map { user -> UserProfileAction.UpdateUser(user) }
    }

}