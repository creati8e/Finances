package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.IncompleteUser
import serg.chuprin.finances.core.api.domain.model.User

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
interface UserRepository {

    fun currentUserSingleFlow(): Flow<User>

    fun updateUser(user: User)

    suspend fun getCurrentUser(): User

    /**
     * @return user who has not completed onboarding.
     * This method should not be called outside user onboarding flow.
     */
    suspend fun getIncompleteUser(): IncompleteUser

}