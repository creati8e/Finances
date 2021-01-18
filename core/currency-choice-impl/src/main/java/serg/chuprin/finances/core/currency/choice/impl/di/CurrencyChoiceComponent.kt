package serg.chuprin.finances.core.currency.choice.impl.di

import dagger.Binds
import dagger.Component
import dagger.Module
import serg.chuprin.finances.core.currency.choice.api.di.CurrencyChoiceStoreApi
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreFactoryApi
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceStoreFactoryApiImpl

/**
 * Created by Sergey Chuprin on 19.01.2021.
 */
@Component(
    modules = [CurrencyChoiceModule::class],
    dependencies = [CurrencyChoiceDependencies::class]
)
interface CurrencyChoiceComponent : CurrencyChoiceStoreApi {

    companion object {

        fun get(dependencies: CurrencyChoiceDependencies): CurrencyChoiceComponent {
            return DaggerCurrencyChoiceComponent
                .builder()
                .currencyChoiceDependencies(dependencies)
                .build()
        }

    }

}

@Module
internal interface CurrencyChoiceModule {

    @Binds
    fun bindsCurrencyChoiceStoreFactory(
        impl: CurrencyChoiceStoreFactoryApiImpl
    ): CurrencyChoiceStoreFactoryApi


}