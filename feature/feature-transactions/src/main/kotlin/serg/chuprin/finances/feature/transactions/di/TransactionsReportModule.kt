package serg.chuprin.finances.feature.transactions.di

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.transactions.data.repository.impl.TransactionReportDataPeriodRepositoryImpl
import serg.chuprin.finances.feature.transactions.data.repository.impl.TransactionReportFilterRepositoryImpl
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportDataPeriodRepository
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
@Module
interface TransactionsReportModule {

    @[Binds ScreenScope]
    fun bindsFilterRepository(
        impl: TransactionReportFilterRepositoryImpl
    ): TransactionReportFilterRepository

    @[Binds ScreenScope]
    fun bindsTransactionReportDataPeriodRepository(
        impl: TransactionReportDataPeriodRepositoryImpl
    ): TransactionReportDataPeriodRepository

}