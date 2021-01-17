package serg.chuprin.finances.feature.moneyaccounts.di

import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.MoneyAccountBalanceService
import serg.chuprin.finances.core.api.presentation.builder.TransitionNameBuilder
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.feature.moneyaccounts.presentation.MoneyAccountsListNavigation

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
interface MoneyAccountsListDependencies : FeatureDependencies {
    val userRepository: UserRepository
    val moneyAccountRepository: MoneyAccountRepository

    val amountFormatter: AmountFormatter

    val moneyAccountBalanceService: MoneyAccountBalanceService

    val moneyAccountsListNavigation: MoneyAccountsListNavigation

    val transitionNameBuilder: TransitionNameBuilder

}