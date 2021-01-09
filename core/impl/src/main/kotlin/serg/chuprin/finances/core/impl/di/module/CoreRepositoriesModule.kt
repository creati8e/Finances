package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.api.domain.repository.*
import serg.chuprin.finances.core.impl.data.repository.*

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
@Module
internal interface CoreRepositoriesModule {

    @[Binds AppScope]
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @[Binds AppScope]
    fun bindCurrencyRepository(impl: CurrencyRepositoryImpl): CurrencyRepository

    @[Binds AppScope]
    fun bindOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository

    @[Binds AppScope]
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @[Binds AppScope]
    fun bindMoneyAccountRepository(impl: MoneyAccountRepositoryImpl): MoneyAccountRepository

    @[Binds AppScope]
    fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @[Binds]
    fun bindDataRepository(impl: DataRepositoryImpl): DataRepository

}