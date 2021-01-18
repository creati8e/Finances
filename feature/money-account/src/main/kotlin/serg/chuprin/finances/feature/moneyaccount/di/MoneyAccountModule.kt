package serg.chuprin.finances.feature.moneyaccount.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreProvider
import serg.chuprin.finances.feature.moneyaccount.presentation.model.CurrencyChoiceStoreBootstrapperImpl
import serg.chuprin.finances.feature.moneyaccount.presentation.model.store.MoneyAccountStore
import serg.chuprin.finances.feature.moneyaccount.presentation.model.store.MoneyAccountStoreFactory

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
@Module
interface MoneyAccountModule {

    companion object {

        @[Provides ScreenScope]
        fun provideCurrencyChoiceStore(
            provider: CurrencyChoiceStoreProvider,
            storeBootstrapper: CurrencyChoiceStoreBootstrapper
        ): CurrencyChoiceStore {
            return provider.provide(storeBootstrapper)
        }

        @[Provides ScreenScope]
        fun provideMoneyAccountStore(factory: MoneyAccountStoreFactory): MoneyAccountStore {
            return factory.create()
        }

    }

    @[Binds ScreenScope]
    fun bindsCurrencyChoiceStoreBootstrapper(
        impl: CurrencyChoiceStoreBootstrapperImpl
    ): CurrencyChoiceStoreBootstrapper

}