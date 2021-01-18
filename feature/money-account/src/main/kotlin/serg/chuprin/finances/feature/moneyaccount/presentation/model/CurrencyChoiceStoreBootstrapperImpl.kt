package serg.chuprin.finances.feature.moneyaccount.presentation.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreInitialParams
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 03.07.2020.
 */
class CurrencyChoiceStoreBootstrapperImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val currencyRepository: CurrencyRepository,
    private val screenArguments: MoneyAccountScreenArguments,
    private val moneyAccountRepository: MoneyAccountRepository
) : CurrencyChoiceStoreBootstrapper {

    override fun bootstrap(): Flow<CurrencyChoiceStoreInitialParams> {
        return flowOfSingleValue {
            CurrencyChoiceStoreInitialParams(
                chosenCurrency = getDefaultCurrency(),
                availableCurrencies = currencyRepository.getAvailableCurrencies()
            )
        }
    }

    private suspend fun getDefaultCurrency(): Currency {
        return when (screenArguments) {
            is MoneyAccountScreenArguments.Editing -> {
                val accountFlow = moneyAccountRepository.accountFlow(screenArguments.moneyAccountId)
                accountFlow.first()?.currency ?: userRepository.getCurrentUser().defaultCurrency
            }
            is MoneyAccountScreenArguments.Creation -> {
                userRepository.getCurrentUser().defaultCurrency
            }
        }
    }

}