package serg.chuprin.finances.feature.moneyaccount.creation.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreProvider
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.CurrencyChoiceStoreBootstrapperImpl

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
@Module
interface MoneyAccountCreationModule {

    companion object {

        @[Provides ScreenScope]
        fun provideCurrencyChoiceStore(
            provider: CurrencyChoiceStoreProvider,
            storeBootstrapper: CurrencyChoiceStoreBootstrapper
        ): CurrencyChoiceStore {
            return provider.provide(storeBootstrapper)
        }

    }

    @[Binds ScreenScope]
    fun bindsCurrencyChoiceStoreBootstrapper(
        impl: CurrencyChoiceStoreBootstrapperImpl
    ): CurrencyChoiceStoreBootstrapper

}