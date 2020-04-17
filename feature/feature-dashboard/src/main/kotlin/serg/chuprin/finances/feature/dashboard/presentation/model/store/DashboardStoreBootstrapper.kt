package serg.chuprin.finances.feature.dashboard.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class DashboardStoreBootstrapper @Inject constructor(
    private val userRepository: UserRepository
) : StoreBootstrapper<DashboardAction> {

    override fun invoke(): Flow<DashboardAction> {
        return userRepository
            .currentUserSingleFlow()
            .map { user -> DashboardAction.UpdateUser(user) }
    }

}