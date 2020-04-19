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
internal abstract class CoreRepositoriesModule {

    @[Binds AppScope]
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @[Binds AppScope]
    abstract fun bindCurrencyRepository(impl: CurrencyRepositoryImpl): CurrencyRepository

    @[Binds AppScope]
    abstract fun bindOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository

    @[Binds AppScope]
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @[Binds AppScope]
    abstract fun bindMoneyAccountRepository(impl: MoneyAccountRepositoryImpl): MoneyAccountRepository

    @[Binds AppScope]
    abstract fun bindTransactionCategoryRepository(
        impl: TransactionCategoryRepositoryImpl
    ): TransactionCategoryRepository

}