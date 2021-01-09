package serg.chuprin.finances.feature.transactions.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.transactions.data.repository.impl.TransactionReportFilterRepositoryImpl
import serg.chuprin.finances.feature.transactions.domain.repository.TransactionReportFilterRepository
import serg.chuprin.finances.feature.transactions.presentation.model.store.TransactionsReportStore
import serg.chuprin.finances.feature.transactions.presentation.model.store.TransactionsReportStoreFactory

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
@Module
interface TransactionsReportModule {

    companion object {

        @[Provides ScreenScope]
        fun provideStore(factory: TransactionsReportStoreFactory): TransactionsReportStore {
            return factory.create()
        }

    }

    @[Binds ScreenScope]
    fun bindsFilterRepository(
        impl: TransactionReportFilterRepositoryImpl
    ): TransactionReportFilterRepository

}