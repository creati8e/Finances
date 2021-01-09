package serg.chuprin.finances.feature.moneyaccounts.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.store.MoneyAccountsListStore
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.store.MoneyAccountsListStoreFactory

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
@Module
object MoneyAccountsListModule {

    @[Provides ScreenScope]
    fun provideStore(factory: MoneyAccountsListStoreFactory): MoneyAccountsListStore {
        return factory.create()
    }

}