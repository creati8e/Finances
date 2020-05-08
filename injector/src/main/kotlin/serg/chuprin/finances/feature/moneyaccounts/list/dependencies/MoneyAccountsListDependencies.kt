package serg.chuprin.finances.feature.moneyaccounts.list.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.MoneyAccountService
import serg.chuprin.finances.core.api.presentation.builder.TransitionNameBuilder
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.navigation.MoneyAccountsListNavigation

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
interface MoneyAccountsListDependencies {
    val userRepository: UserRepository
    val moneyAccountRepository: MoneyAccountRepository

    val amountFormatter: AmountFormatter

    val moneyAccountService: MoneyAccountService

    val moneyAccountsListNavigation: MoneyAccountsListNavigation

    val transitionNameBuilder: TransitionNameBuilder

}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface MoneyAccountsListDependenciesComponent :
    MoneyAccountsListDependencies