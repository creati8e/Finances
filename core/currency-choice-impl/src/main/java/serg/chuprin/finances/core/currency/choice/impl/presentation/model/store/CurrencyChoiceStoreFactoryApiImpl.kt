package serg.chuprin.finances.core.currency.choice.impl.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreInitialParams
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreFactoryApi
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Sergey Chuprin on 09.06.2020.
 */
internal class CurrencyChoiceStoreFactoryApiImpl @Inject constructor(
    private val executorProvider: Provider<CurrencyChoiceActionExecutor>
) : CurrencyChoiceStoreFactoryApi {

    override fun create(bootstrapper: CurrencyChoiceStoreBootstrapper): CurrencyChoiceStore {
        return CurrencyChoiceStoreFactory(
            actionExecutor = executorProvider.get(),
            bootstrapper = bootstrapper.wrap()
        ).create()
    }

    private fun CurrencyChoiceStoreBootstrapper.wrap(): () -> Flow<CurrencyChoiceAction> {
        return {
            bootstrap().map { initialParams: CurrencyChoiceStoreInitialParams ->
                CurrencyChoiceAction.SetCurrenciesParams(
                    chosenCurrency = initialParams.chosenCurrency,
                    availableCurrencies = initialParams.availableCurrencies
                )
            }
        }
    }

}