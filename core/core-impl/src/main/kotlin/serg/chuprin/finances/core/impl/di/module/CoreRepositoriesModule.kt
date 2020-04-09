package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.impl.data.repository.CurrencyRepositoryImpl
import serg.chuprin.finances.core.impl.data.repository.OnboardingRepositoryImpl
import serg.chuprin.finances.core.impl.data.repository.TransactionRepositoryImpl
import serg.chuprin.finances.core.impl.data.repository.UserRepositoryImpl

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

}