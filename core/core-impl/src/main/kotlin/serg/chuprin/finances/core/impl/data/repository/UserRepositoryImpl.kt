package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import serg.chuprin.finances.core.api.domain.model.IncompleteUser
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.impl.data.database.firebase.datasource.FirebaseUserDataSource
import serg.chuprin.finances.core.impl.data.mapper.IncompleteUserMapper
import serg.chuprin.finances.core.impl.data.mapper.UserMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class UserRepositoryImpl @Inject constructor(
    private val userMapper: UserMapper,
    private val dataSource: FirebaseUserDataSource,
    private val incompleteUserMapper: IncompleteUserMapper
) : UserRepository {

    override fun currentUserSingleFlow(): Flow<User> {
        return dataSource
            .currentUserSingleFlow()
            .mapNotNull { documentSnapshot -> userMapper(documentSnapshot) }
            .flowOn(Dispatchers.Default)
    }

    override suspend fun updateUser(user: User) = dataSource.updateUser(user)

    override suspend fun getIncompleteUser(): IncompleteUser {
        return incompleteUserMapper(dataSource.getIncompleteUser())!!
    }

}