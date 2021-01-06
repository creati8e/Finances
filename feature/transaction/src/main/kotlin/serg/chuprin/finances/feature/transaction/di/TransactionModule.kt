package serg.chuprin.finances.feature.transaction.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionStore
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionStoreFactory

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
@Module
object TransactionModule {

    @[Provides ScreenScope]
    fun provideStore(factory: TransactionStoreFactory): TransactionStore = factory.create()

}