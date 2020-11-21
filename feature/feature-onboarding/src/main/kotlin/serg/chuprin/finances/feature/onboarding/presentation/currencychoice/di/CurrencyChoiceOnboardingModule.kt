package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreFactory
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.CurrencyChoiceStoreBootstrapperImpl

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
@Module
interface CurrencyChoiceOnboardingModule {

    companion object {

        @[Provides ScreenScope]
        fun provideCurrencyChoiceStore(
            factory: CurrencyChoiceStoreFactory,
            storeBootstrapper: CurrencyChoiceStoreBootstrapper
        ): CurrencyChoiceStore {
            return factory.create(storeBootstrapper)
        }

    }

    @[Binds ScreenScope]
    fun bindsCurrencyChoiceStoreBootstrapper(
        impl: CurrencyChoiceStoreBootstrapperImpl
    ): CurrencyChoiceStoreBootstrapper

}