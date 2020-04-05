package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.User

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
interface UserRepository {

    suspend fun setCurrentUser(user: User)

    fun currentUserSingleFlow(): Flow<User>

}