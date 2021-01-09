package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.api.domain.service.MoneyAccountBalanceService
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.impl.domain.service.MoneyAccountBalanceServiceImpl
import serg.chuprin.finances.core.impl.domain.service.TransactionCategoryRetrieverServiceImpl

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
@Module
internal interface CoreServicesModule {

    @[Binds AppScope]
    fun bindTransactionCategoryRetrieverService(
        impl: TransactionCategoryRetrieverServiceImpl
    ): TransactionCategoryRetrieverService

    @[Binds AppScope]
    fun bindMoneyAccountService(
        impl: MoneyAccountBalanceServiceImpl
    ): MoneyAccountBalanceService

}