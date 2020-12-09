package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
// FIXME: 09.12.2020
//object CurrencyChoiceOnboardingStoreTest : Spek({
//
//    Feature("Currency choice onboarding store") {
//
//        Scenario("Starting store") {
//
//            val params = CurrencyChoiceOnboardingStoreFactory.build()
//            val store = params.store
//
//            val defaultCurrency = availableCurrencies.first()
//
//            Given("Default currency is USD and has other currencies available") {
//                params.currencyRepository.mockCurrencies(defaultCurrency)
//            }
//
//            When("Store is started") {
//                store.testStart()
//            }
//
//            Then("Initial state contains bootstrapped value as currently chosen by default") {
//                expectThat(store.state.chosenCurrency).isEqualTo(defaultCurrency)
//            }
//
//            And("State contains cells for all available cells") {
//                store.state.currentCells.assertContainsCurrencies(availableCurrencies)
//            }
//
//            And("Current cells are equals to default cells") {
//                expectThat(store.state.currentCells).isEqualTo(store.state.defaultCurrencyCells)
//            }
//
//            And("Only single cell is chosen and equals to default") {
//                store.state.currentCells.assertContainsSingleChosenCurrency(defaultCurrency)
//            }
//
//            And("Currency picker is not visible") {
//                expectThat(store.state.currencyPickerIsVisible).isFalse()
//            }
//
//            And("Done button is enabled") {
//                expectThat(store.state.doneButtonIsEnabled).isTrue()
//            }
//
//        }
//
//
//        Scenario("Choosing currency from currency picker") {
//
//            val params = CurrencyChoiceOnboardingStoreFactory.build()
//            val store = params.store
//
//            val defaultCurrency = availableCurrencies.first()
//
//            Given("Store is started") {
//                params.currencyRepository.mockCurrencies(defaultCurrency)
//                store.testStart()
//            }
//
//            When("User opened currency picker") {
//                store.dispatch(CurrencyChoiceOnboardingIntent.ClickOnCurrencyPicker)
//            }
//
//            Then("New state is produced with opened currency picker") {
//                expectThat(store.state.currencyPickerIsVisible).isTrue()
//            }
//
//            And("Cells in currency picker contains all available cells with default chosen") {
//                store.state.currentCells.assertContainsSingleChosenCurrency(defaultCurrency)
//            }
//
//            val newChosenCurrency = availableCurrencies.last()
//            val newChosenCurrencyCell = CurrencyCell(
//                isChosen = true,
//                currency = newChosenCurrency,
//                displayName = newChosenCurrency.displayName
//            )
//
//            When("New currency is picked from picker") {
//                store.dispatch(CurrencyChoiceOnboardingIntent.ChooseCurrency(newChosenCurrencyCell))
//            }
//
//            Then("Currency's name is displayed") {
//                expectThat(store.state.chosenCurrencyDisplayName)
//                    .isEqualTo(newChosenCurrencyCell.displayName)
//            }
//
//            And("Currency picker is closed") {
//                expectThat(store.state.currencyPickerIsVisible).isFalse()
//            }
//
//            And("Default cells and current cells are updated with new chosen currency") {
//                with(store.state) {
//                    currentCells.assertContainsSingleChosenCurrency(newChosenCurrency)
//                    defaultCurrencyCells.assertContainsSingleChosenCurrency(newChosenCurrency)
//                    expectThat(currentCells).containsExactly(defaultCurrencyCells)
//                }
//            }
//
//        }
//
//
//        Scenario("Search currencies in currency picker") {
//
//            val params = CurrencyChoiceOnboardingStoreFactory.build()
//            val currencyRepository = params.currencyRepository
//            val store = params.store
//
//            val defaultCurrency = Currency.getInstance("USD")
//
//            val firstSearchQuery = "u"
//            val secondSearchQuery = "R"
//            val thirdSearchQuery = "dsfshg32"
//
//            Given("Currency picker is opened") {
//                with(currencyRepository) {
//                    mockCurrencies(defaultCurrency)
//                    mockCurrenciesSearch(firstSearchQuery)
//                    mockCurrenciesSearch(secondSearchQuery)
//                    mockCurrenciesSearch(thirdSearchQuery)
//                }
//                with(store) {
//                    testStart()
//                    dispatch(CurrencyChoiceOnboardingIntent.ClickOnCurrencyPicker)
//                }
//            }
//
//            When("User enters first search query") {
//                with(store) {
//                    dispatch(CurrencyChoiceOnboardingIntent.SearchCurrencies(firstSearchQuery))
//                    backgroundTestDispatcher.advanceTimeBy(300)
//                }
//            }
//
//            Then("New cells are produced") {
//                val filteredCurrencies = availableCurrencies.filterBySearchQuery(firstSearchQuery)
//                store.state.currentCells.assertContainsCurrencies(filteredCurrencies)
//            }
//
//            And("Chosen currency contains in list too") {
//                store.state.currentCells.assertContainsSingleChosenCurrency(defaultCurrency)
//            }
//
//            When("User enters second search query") {
//                with(store) {
//                    dispatch(CurrencyChoiceOnboardingIntent.SearchCurrencies(secondSearchQuery))
//                    backgroundTestDispatcher.advanceTimeBy(300)
//                }
//            }
//
//            Then("New cells list is produces again") {
//                val filteredCurrencies = availableCurrencies.filterBySearchQuery(secondSearchQuery)
//                store.state.currentCells.assertContainsCurrencies(filteredCurrencies)
//            }
//
//            When("Users enters third search query") {
//                with(store) {
//                    dispatch(CurrencyChoiceOnboardingIntent.SearchCurrencies(thirdSearchQuery))
//                    backgroundTestDispatcher.advanceTimeBy(300)
//                }
//            }
//
//            Then("Single zero data cell is produced") {
//                expectThat(store.state.currentCells).one { isA<ZeroDataCell>() }
//            }
//
//            And("Default cells remains the same") {
//                store.state.defaultCurrencyCells.assertContainsCurrencies(availableCurrencies)
//            }
//
//        }
//
//
//        Scenario("Clicking back") {
//
//            val params = CurrencyChoiceOnboardingStoreFactory.build()
//            val store = params.store
//
//            Given("Currency picker is opened") {
//                params.currencyRepository.mockCurrencies(availableCurrencies.first())
//                with(store) {
//                    testStart()
//                    dispatch(CurrencyChoiceOnboardingIntent.ClickOnCurrencyPicker)
//                }
//            }
//
//            When("User pressed back") {
//                store.dispatch(CurrencyChoiceOnboardingIntent.BackPress)
//            }
//
//            Then("Currency picker is closed") {
//                expectThat(store.state.currencyPickerIsVisible).isFalse()
//            }
//
//            When("User pressed back again") {
//                store.dispatch(CurrencyChoiceOnboardingIntent.BackPress)
//            }
//
//            Then("App is closed") {
//                expectThat(store.lastEvent).isA<CurrencyChoiceOnboardingEvent.CloseApp>()
//            }
//
//        }
//
//
//        Scenario("Clicking on 'done' button") {
//
//            val params = CurrencyChoiceOnboardingStoreFactory.build()
//            val store = params.store
//
//            Given("Currency is chosen") {
//                params.currencyRepository.mockCurrencies(availableCurrencies.first())
//                with(store) {
//                    testStart()
//                    dispatch(CurrencyChoiceOnboardingIntent.ClickOnCurrencyPicker)
//                }
//            }
//
//            When("User clicked on 'done' button") {
//                coEvery {
//                    params.completeCurrencyChoiceOnboardingUseCase.execute(store.state.chosenCurrency!!)
//                } just runs
//                store.dispatch(CurrencyChoiceOnboardingIntent.ClickOnDoneButton)
//            }
//
//            Then("User navigated to dashboard after use case execution") {
//                coVerify {
//                    params.completeCurrencyChoiceOnboardingUseCase.execute(store.state.chosenCurrency!!)
//                }
//                expectThat(store.lastEvent).isA<CurrencyChoiceOnboardingEvent.NavigateToAccountsSetup>()
//            }
//
//        }
//
//    }
//
//})
//
//private fun List<Currency>.filterBySearchQuery(searchQuery: String): List<Currency> {
//    return filter { it.currencyCode.contains(searchQuery, ignoreCase = true) }
//}
//
//private fun CurrencyRepository.mockCurrenciesSearch(searchQuery: String) {
//    coEvery {
//        searchCurrencies(searchQuery)
//    } returns availableCurrencies.filterBySearchQuery(searchQuery)
//}
//
//private fun CurrencyRepository.mockCurrencies(
//    defaultCurrency: Currency
//) {
//    coEvery { getDefaultCurrency() } returns defaultCurrency
//    coEvery { getAvailableCurrencies() } returns availableCurrencies
//}
//
//private val availableCurrencies = listOf(
//    Currency.getInstance("USD"),
//    Currency.getInstance("RUB"),
//    Currency.getInstance("EUR"),
//    Currency.getInstance("AUD")
//)
//
//private fun List<BaseCell>.assertContainsCurrencies(currencies: List<Currency>) {
//    expectThat(this)
//        .all { isA<CurrencyCell>() }
//        .map { (it as CurrencyCell).currency }
//        .containsExactlyInAnyOrder(currencies)
//}
//
//private fun List<BaseCell>.assertContainsSingleChosenCurrency(chosenCurrency: Currency) {
//    expectThat(this)
//        .one {
//            isA<CurrencyCell>()
//                .and {
//                    get { isChosen }.isEqualTo(true)
//                }
//                .and {
//                    get { currency }.isEqualTo(chosenCurrency)
//                }
//        }
//}