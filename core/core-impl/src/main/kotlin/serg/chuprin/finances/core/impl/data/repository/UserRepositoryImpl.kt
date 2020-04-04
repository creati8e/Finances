package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class UserRepositoryImpl @Inject constructor() : UserRepository {

    override fun currentUserSingleFlow(): Flow<User> {
        TODO("Not yet implemented")
    }

}