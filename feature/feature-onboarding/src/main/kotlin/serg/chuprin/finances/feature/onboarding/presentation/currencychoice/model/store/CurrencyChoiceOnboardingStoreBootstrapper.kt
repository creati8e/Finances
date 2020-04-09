package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.extensions.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.model.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class CurrencyChoiceOnboardingStoreBootstrapper @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : StoreBootstrapper<CurrencyChoiceOnboardingAction> {

    @OptIn(FlowPreview::class)
    override fun invoke(): Flow<CurrencyChoiceOnboardingAction> {
        return flowOfSingleValue {
            val defaultCurrency = currencyRepository.getDefaultCurrency()
            val availableCurrencies = currencyRepository.getAvailableCurrencies()
            CurrencyChoiceOnboardingAction.SetCurrenciesParams(
                currentCurrency = defaultCurrency,
                availableCurrencies = availableCurrencies
            )
        }
    }

}