package serg.chuprin.finances.feature.moneyaccount.details.di

import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsStore
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store.MoneyAccountDetailsStoreFactory

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
@Module
object MoneyAccountDetailsModule {

    @[Provides ScreenScope]
    fun provideStore(factory: MoneyAccountDetailsStoreFactory): MoneyAccountDetailsStore {
        return factory.create()
    }

}