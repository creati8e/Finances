package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceState
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreBootstrapper
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceStoreInitialParams
import serg.chuprin.finances.core.currency.choice.impl.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceAction
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceActionExecutor
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceEffect
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.CurrencyChoiceStateReducer
import serg.chuprin.finances.core.test.presentation.mvi.store.TestStoreImpl
import serg.chuprin.finances.feature.moneyaccount.presentation.model.CurrencyChoiceStoreBootstrapperImpl
import java.util.*

/**
 * Created by Sergey Chuprin on 19.01.2021.
 */
object CurrencyChoiceTestStoreFactory {

    private val availableCurrencies by lazy {
        Currency.getAvailableCurrencies().toList()
    }

    fun create(
        screenArguments: MoneyAccountScreenArguments,
        moneyAccountRepository: MoneyAccountRepository
    ): CurrencyChoiceTestStore {
        return object :
            TestStoreImpl<CurrencyChoiceIntent, CurrencyChoiceEffect, CurrencyChoiceAction, CurrencyChoiceState, Nothing>(
                CurrencyChoiceState(),
                CurrencyChoiceStateReducer(),
                CurrencyChoiceStoreBootstrapperImpl(
                    mockUserRepository(),
                    mockCurrencyRepository(),
                    screenArguments,
                    moneyAccountRepository
                ).wrap(),
                createActionExecutor(),
                CurrencyChoiceAction::ExecuteIntent
            ), CurrencyChoiceTestStore {}
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

    private fun createActionExecutor(): CurrencyChoiceActionExecutor {
        return CurrencyChoiceActionExecutor(SearchCurrenciesUseCase(mockCurrencyRepository()))
    }

    private fun mockCurrencyRepository(): CurrencyRepository {
        val currencyRepository = mockk<CurrencyRepository>()

        val querySlot = slot<String>()
        coEvery { currencyRepository.searchCurrencies(capture(querySlot)) } answers {
            availableCurrencies.filter { currency ->
                currency.currencyCode.contains(querySlot.captured, ignoreCase = true)
            }
        }

        coEvery { currencyRepository.getAvailableCurrencies() } returns availableCurrencies

        return currencyRepository
    }

    private fun mockUserRepository(): UserRepository {
        return mockk<UserRepository>().apply {
            coEvery { getCurrentUser() } answers {
                User(
                    id = Id.existing("user_id"),
                    email = "email",
                    photoUrl = "",
                    displayName = "name",
                    dataPeriodType = DataPeriodType.MONTH,
                    defaultCurrencyCode = "USD"
                )
            }
        }
    }

}