package serg.chuprin.finances.feature.moneyaccounts.list

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreRepositoriesProvider
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
interface MoneyAccountsListDependencies {
    val userRepository: UserRepository
    val moneyAccountRepository: MoneyAccountRepository
}

@Component(dependencies = [CoreRepositoriesProvider::class])
internal interface MoneyAccountsListDependenciesComponent : MoneyAccountsListDependencies {

    @Component.Factory
    interface Factory {

        fun repositoriesProvider(
            repositoriesProvider: CoreRepositoriesProvider
        ): MoneyAccountsListDependenciesComponent

    }

}