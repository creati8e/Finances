package serg.chuprin.finances.core.currency.choice.impl.presentation.model.store

import io.mockk.coEvery
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.core.currency.choice.impl.presentation.model.store.factory.CurrencyChoiceStoreTestFactory
import strikt.api.expectThat
import strikt.assertions.*
import java.util.*

/**
 * Created by Sergey Chuprin on 09.12.2020.
 */
object CurrencyChoiceStoreTest : Spek({

    Feature("Currency choice store") {

        Scenario("Starting store") {

            val defaultCurrency = availableCurrencies.first()

            val (store, currencyRepository) = CurrencyChoiceStoreTestFactory.build()

            Given("Default currency is USD and has other currencies available") {
                currencyRepository.mockCurrencies(defaultCurrency)
            }

            When("Store is started") {
                store.start()
            }

            Then("Initial state contains bootstrapped value as currently chosen by default") {
                expectThat(store.state.chosenCurrency).isEqualTo(defaultCurrency)
            }

            And("State contains cells for all available cells") {
                store.state.currentCells.assertContainsCurrencies(availableCurrencies)
            }

            And("Current cells are equals to default cells") {
                expectThat(store.state.currentCells).isEqualTo(store.state.defaultCurrencyCells)
            }

            And("Only single cell is chosen and equals to default") {
                store.state.currentCells.assertContainsSingleChosenCurrency(defaultCurrency)
            }

            And("Currency picker is not visible") {
                expectThat(store.state.currencyPickerIsVisible).isFalse()
            }

        }

        Scenario("Choosing currency from currency picker") {

            val (store, currencyRepository) = CurrencyChoiceStoreTestFactory.build()

            val defaultCurrency = availableCurrencies.first()

            Given("Store is started") {
                currencyRepository.mockCurrencies(defaultCurrency)
                store.start()
            }

            When("User opened currency picker") {
                store.dispatch(CurrencyChoiceIntent.ClickOnCurrencyPicker)
            }

            Then("New state is produced with opened currency picker") {
                expectThat(store.state.currencyPickerIsVisible).isTrue()
            }

            And("Cells in currency picker contains all available cells with default chosen") {
                store.state.currentCells.assertContainsSingleChosenCurrency(defaultCurrency)
            }

            val newChosenCurrency = availableCurrencies.last()
            val newChosenCurrencyCell =
                serg.chuprin.finances.core.currency.choice.api.presentation.model.cells.CurrencyCell(
                    isChosen = true,
                    currency = newChosenCurrency,
                    displayName = newChosenCurrency.displayName
                )

            When("New currency is picked from picker") {
                store.dispatch(CurrencyChoiceIntent.ClickOnCurrencyCell(newChosenCurrencyCell))
            }

            Then("Currency's name is displayed") {
                expectThat(store.state.chosenCurrencyDisplayName)
                    .isEqualTo(newChosenCurrencyCell.displayName)
            }

            And("Currency picker is closed") {
                expectThat(store.state.currencyPickerIsVisible).isFalse()
            }

            And("Default cells and current cells are updated with new chosen currency") {
                with(store.state) {
                    currentCells.assertContainsSingleChosenCurrency(newChosenCurrency)
                    defaultCurrencyCells.assertContainsSingleChosenCurrency(newChosenCurrency)
                    expectThat(currentCells).containsExactly(defaultCurrencyCells)
                }
            }

        }


        Scenario("Search currencies in currency picker") {

            val (store, currencyRepository) = CurrencyChoiceStoreTestFactory.build()

            val defaultCurrency = Currency.getInstance("USD")

            val firstSearchQuery = "u"
            val secondSearchQuery = "R"
            val thirdSearchQuery = "dsfshg32"

            Given("Currency picker is opened") {
                with(currencyRepository) {
                    mockCurrencies(defaultCurrency)
                    mockCurrenciesSearch(firstSearchQuery)
                    mockCurrenciesSearch(secondSearchQuery)
                    mockCurrenciesSearch(thirdSearchQuery)
                }
                with(store) {
                    start()
                    dispatch(CurrencyChoiceIntent.ClickOnCurrencyPicker)
                }
            }

            When("User enters first search query") {
                with(store) {
                    dispatch(CurrencyChoiceIntent.EnterCurrencySearchQuery(firstSearchQuery))
                    backgroundTestDispatcher.advanceTimeBy(300)
                }
            }

            Then("New cells are produced") {
                val filteredCurrencies = availableCurrencies.filterBySearchQuery(firstSearchQuery)
                store.state.currentCells.assertContainsCurrencies(filteredCurrencies)
            }

            And("Chosen currency contains in list too") {
                store.state.currentCells.assertContainsSingleChosenCurrency(defaultCurrency)
            }

            When("User enters second search query") {
                with(store) {
                    dispatch(CurrencyChoiceIntent.EnterCurrencySearchQuery(secondSearchQuery))
                    backgroundTestDispatcher.advanceTimeBy(300)
                }
            }

            Then("New cells list is produces again") {
                val filteredCurrencies = availableCurrencies.filterBySearchQuery(secondSearchQuery)
                store.state.currentCells.assertContainsCurrencies(filteredCurrencies)
            }

            When("Users enters third search query") {
                with(store) {
                    dispatch(CurrencyChoiceIntent.EnterCurrencySearchQuery(thirdSearchQuery))
                    backgroundTestDispatcher.advanceTimeBy(300)
                }
            }

            Then("Single zero data cell is produced") {
                expectThat(store.state.currentCells).one { isA<ZeroDataCell>() }
            }

            And("Default cells remains the same") {
                store.state.defaultCurrencyCells.assertContainsCurrencies(availableCurrencies)
            }

        }


        Scenario("Closing currency picker") {

            val (store, currencyRepository) = CurrencyChoiceStoreTestFactory.build()

            Given("Currency picker is opened") {
                currencyRepository.mockCurrencies(availableCurrencies.first())
                with(store) {
                    start()
                    dispatch(CurrencyChoiceIntent.ClickOnCurrencyPicker)
                }
            }

            When("User pressed back") {
                store.dispatch(CurrencyChoiceIntent.ClickOnCloseCurrencyPicker)
            }

            Then("Currency picker is closed") {
                expectThat(store.state.currencyPickerIsVisible).isFalse()
            }

        }

    }

})

private fun List<Currency>.filterBySearchQuery(searchQuery: String): List<Currency> {
    return filter { it.currencyCode.contains(searchQuery, ignoreCase = true) }
}

private fun CurrencyRepository.mockCurrenciesSearch(searchQuery: String) {
    coEvery {
        searchCurrencies(searchQuery)
    } returns availableCurrencies.filterBySearchQuery(searchQuery)
}

private fun CurrencyRepository.mockCurrencies(defaultCurrency: Currency) {
    coEvery { getDefaultCurrency() } returns defaultCurrency
    coEvery { getAvailableCurrencies() } returns availableCurrencies
}

private val availableCurrencies = listOf(
    Currency.getInstance("USD"),
    Currency.getInstance("RUB"),
    Currency.getInstance("EUR"),
    Currency.getInstance("AUD")
)

private fun List<BaseCell>.assertContainsCurrencies(currencies: List<Currency>) {
    expectThat(this)
        .all { isA<serg.chuprin.finances.core.currency.choice.api.presentation.model.cells.CurrencyCell>() }
        .map { (it as serg.chuprin.finances.core.currency.choice.api.presentation.model.cells.CurrencyCell).currency }
        .containsExactlyInAnyOrder(currencies)
}

private fun List<BaseCell>.assertContainsSingleChosenCurrency(chosenCurrency: Currency) {
    expectThat(this)
        .one {
            isA<serg.chuprin.finances.core.currency.choice.api.presentation.model.cells.CurrencyCell>()
                .and {
                    get { isChosen }.isEqualTo(true)
                }
                .and {
                    get { currency }.isEqualTo(chosenCurrency)
                }
        }
}