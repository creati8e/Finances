package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.User

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
interface UserRepository {

    /**
     * @return true if user is new.
     */
    suspend fun createAndSet(user: User): Boolean

    fun currentUserSingleFlow(): Flow<User>

}