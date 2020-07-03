package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreFactory
import serg.chuprin.finances.core.impl.presentation.model.store.currencychoice.CurrencyChoiceStoreFactoryImpl

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
@Module
internal interface CoreStoreModule {

    @Binds
    fun bindsCurrencyChoiceStoreFactory(
        impl: CurrencyChoiceStoreFactoryImpl
    ): CurrencyChoiceStoreFactory

}