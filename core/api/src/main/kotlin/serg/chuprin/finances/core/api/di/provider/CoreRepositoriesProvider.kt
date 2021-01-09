package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.domain.repository.*

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
interface CoreRepositoriesProvider {

    val dataRepository: DataRepository

    val userRepository: UserRepository

    val currencyRepository: CurrencyRepository

    val onboardingRepository: OnboardingRepository

    val transactionRepository: TransactionRepository

    val moneyAccountRepository: MoneyAccountRepository

    val categoryRepository: CategoryRepository

}