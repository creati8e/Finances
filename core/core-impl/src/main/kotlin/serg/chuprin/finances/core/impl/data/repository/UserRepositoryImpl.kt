package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import serg.chuprin.finances.core.api.domain.model.IncompleteUser
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.impl.data.datasource.firebase.FirebaseUserDataSource
import serg.chuprin.finances.core.impl.data.mapper.user.FirebaseIncompleteUserMapper
import serg.chuprin.finances.core.impl.data.mapper.user.FirebaseUserMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class UserRepositoryImpl @Inject constructor(
    private val userMapper: FirebaseUserMapper,
    private val dataSource: FirebaseUserDataSource,
    private val incompleteUserMapper: FirebaseIncompleteUserMapper
) : UserRepository {

    override fun currentUserSingleFlow(): Flow<User> {
        return dataSource
            .currentUserSingleFlow()
            .mapNotNull { documentSnapshot -> userMapper.mapFromSnapshot(documentSnapshot) }
            .flowOn(Dispatchers.Default)
    }

    override fun updateUser(user: User) = dataSource.updateUser(user)

    override suspend fun getCurrentUser(): User {
        return userMapper.mapFromSnapshot(dataSource.getCurrentUser())!!
    }

    override suspend fun getIncompleteUser(): IncompleteUser {
        return incompleteUserMapper.mapTo(dataSource.getIncompleteUser())!!
    }

}