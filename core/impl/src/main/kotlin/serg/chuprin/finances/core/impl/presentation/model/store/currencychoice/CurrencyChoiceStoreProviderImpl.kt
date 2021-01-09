package serg.chuprin.finances.core.impl.presentation.model.store.currencychoice

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreInitialParams
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Sergey Chuprin on 09.06.2020.
 */
internal class CurrencyChoiceStoreProviderImpl @Inject constructor(
    private val executorProvider: Provider<CurrencyChoiceActionExecutor>
) : CurrencyChoiceStoreProvider {

    override fun provide(bootstrapper: CurrencyChoiceStoreBootstrapper): CurrencyChoiceStore {
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