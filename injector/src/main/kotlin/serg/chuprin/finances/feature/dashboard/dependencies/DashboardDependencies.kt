package serg.chuprin.finances.feature.dashboard.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreRepositoriesProvider
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface DashboardDependencies {
    val userRepository: UserRepository
    val transactionRepository: TransactionRepository
}

@Component(dependencies = [CoreRepositoriesProvider::class])
internal interface DashboardDependenciesComponent : DashboardDependencies