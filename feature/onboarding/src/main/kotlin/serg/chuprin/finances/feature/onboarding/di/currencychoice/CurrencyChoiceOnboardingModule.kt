package serg.chuprin.finances.feature.onboarding.di.currencychoice

import dagger.Binds
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreFactoryApi
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.CurrencyChoiceStoreBootstrapperImpl

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
@Module
interface CurrencyChoiceOnboardingModule {

    companion object {

        @[Provides ScreenScope]
        fun provideCurrencyChoiceStore(
            provider: CurrencyChoiceStoreFactoryApi,
            storeBootstrapper: CurrencyChoiceStoreBootstrapper
        ): CurrencyChoiceStore {
            return provider.create(storeBootstrapper)
        }

    }

    @[Binds ScreenScope]
    fun bindsCurrencyChoiceStoreBootstrapper(
        impl: CurrencyChoiceStoreBootstrapperImpl
    ): CurrencyChoiceStoreBootstrapper

}