package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.api.domain.MoneyAccountBalanceCalculator
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.impl.domain.MoneyAccountBalanceCalculatorImpl
import serg.chuprin.finances.core.impl.domain.TransactionAmountCalculatorImpl
import serg.chuprin.finances.core.impl.domain.linker.TransactionWithCategoriesLinkerImpl
import serg.chuprin.finances.core.impl.presentation.model.manager.ResourceManagerImpl

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
@Module
internal interface CoreManagersModule {

    @[Binds AppScope]
    fun bindResourceManager(impl: ResourceManagerImpl): ResourceManger

    @[Binds AppScope]
    fun bindTransactionAmountCalculator(
        impl: TransactionAmountCalculatorImpl
    ): TransactionAmountCalculator

    @[Binds AppScope]
    fun bindTransactionWithCategoriesLinker(
        impl: TransactionWithCategoriesLinkerImpl
    ): TransactionWithCategoriesLinker

    @[Binds AppScope]
    fun bindMoneyAccountBalanceCalculator(
        impl: MoneyAccountBalanceCalculatorImpl
    ): MoneyAccountBalanceCalculator

}