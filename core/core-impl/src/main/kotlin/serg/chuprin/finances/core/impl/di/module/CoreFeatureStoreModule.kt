package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.impl.presentation.model.store.currencychoice.CurrencyChoiceStoreImpl

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@Module
internal interface CoreFeatureStoreModule {

    @Binds
    fun bindsCurrencyChoiceStore(impl: CurrencyChoiceStoreImpl): CurrencyChoiceStore

}