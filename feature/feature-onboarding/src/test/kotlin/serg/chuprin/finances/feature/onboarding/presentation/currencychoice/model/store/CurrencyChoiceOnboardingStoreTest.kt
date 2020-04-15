package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import io.mockk.coEvery
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.cells.CurrencyCell
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store.factory.CurrencyChoiceOnboardingStoreFactory
import strikt.api.expectThat
import strikt.assertions.*
import java.util.*

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
object CurrencyChoiceOnboardingStoreTest : Spek({

    Feature("Currency choice onboarding store") {

        Scenario("Starting store") {

            val params = CurrencyChoiceOnboardingStoreFactory.build()
            val store = params.store

            val availableCurrencies = listOf(
                Currency.getInstance("USD"),
                Currency.getInstance("RUB"),
                Currency.getInstance("EUR"),
                Currency.getInstance("AUD")
            )
            val defaultCurrency = availableCurrencies.first()

            Given("Default currency is USD and has other currencies available") {
                coEvery {
                    params.currencyRepository.getDefaultCurrency()
                } returns defaultCurrency

                coEvery {
                    params.currencyRepository.getAvailableCurrencies()
                } returns availableCurrencies
            }

            When("Store is started") {
                store.testSubscribe()
            }

            Then("Initial state contains bootstrapped value as currently chosen by default") {
                expectThat(store.state.chosenCurrency).isEqualTo(defaultCurrency)
            }

            And("State contains cells for all available cells") {
                expectThat(store.state.currentCells)
                    .all { isA<CurrencyCell>() }
                    .map { (it as CurrencyCell).currency }
                    .containsExactlyInAnyOrder(availableCurrencies)
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

            And("Done button is enabled") {
                expectThat(store.state.doneButtonIsEnabled).isTrue()
            }

        }

    }

})

private fun List<BaseCell>.assertContainsSingleChosenCurrency(chosenCurrency: Currency) {
    expectThat(this)
        .one {
            isA<CurrencyCell>()
                .and {
                    get { isChosen }.isEqualTo(true)
                }
                .and {
                    get { currency }.isEqualTo(chosenCurrency)
                }
        }
}