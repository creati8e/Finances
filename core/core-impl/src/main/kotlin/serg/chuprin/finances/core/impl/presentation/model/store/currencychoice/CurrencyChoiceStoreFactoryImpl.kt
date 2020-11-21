package serg.chuprin.finances.core.impl.presentation.model.store.currencychoice

import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStore
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreFactory
import serg.chuprin.finances.core.api.presentation.currencychoice.model.store.CurrencyChoiceStoreInitialParams
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Sergey Chuprin on 09.06.2020.
 */
internal class CurrencyChoiceStoreFactoryImpl @Inject constructor(
    private val executorProvider: Provider<CurrencyChoiceActionExecutor>
) : CurrencyChoiceStoreFactory {

    override fun create(bootstrapper: CurrencyChoiceStoreBootstrapper): CurrencyChoiceStore {
        return CurrencyChoiceStoreImpl(
            executor = executorProvider.get(),
            bootstrapper = {
                bootstrapper.bootstrap().map { initialParams: CurrencyChoiceStoreInitialParams ->
                    CurrencyChoiceAction.SetCurrenciesParams(
                        currentCurrency = initialParams.currentCurrency,
                        availableCurrencies = initialParams.availableCurrencies
                    )
                }
            }
        )
    }

}