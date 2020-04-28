package serg.chuprin.finances.feature.moneyaccounts.list.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
interface MoneyAccountsListDependencies {
    val userRepository: UserRepository
    val moneyAccountRepository: MoneyAccountRepository
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface MoneyAccountsListDependenciesComponent :
    MoneyAccountsListDependencies