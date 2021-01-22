package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.extensions.containsType
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.currency.choice.api.presentation.model.cells.CurrencyCell
import serg.chuprin.finances.core.currency.choice.api.presentation.model.store.CurrencyChoiceIntent
import serg.chuprin.finances.feature.moneyaccount.domain.model.MoneyAccountCreationParams
import serg.chuprin.finances.feature.moneyaccount.domain.model.MoneyAccountEditingParams
import serg.chuprin.finances.feature.moneyaccount.presentation.model.MoneyAccountDefaultData
import strikt.api.expectThat
import strikt.assertions.*
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 18.01.2021.
 */
object MoneyAccountStoreTest : Spek({

    Feature("Money account store") {

        Scenario("Money account creation") {

            val storeParams = MoneyAccountStoreCreator.create(
                MoneyAccountScreenArguments.Creation(transitionName = EMPTY_STRING)
            )
            val moneyAccountStore = storeParams.moneyAccountStore
            val currencyChoiceStore = storeParams.currencyChoiceStore

            When("Store is started") {
                moneyAccountStore.start()
            }

            Then("Correct initial state is produced") {
                expectThat(moneyAccountStore.state) {
                    get("Balance") { balance }.isEqualTo(BigDecimal.ZERO)
                    get("Money account name") { moneyAccountName }.isEmpty()
                    get("Saving button is enabled") { savingButtonIsEnabled }.isFalse()
                    get("Money account default data") { moneyAccountDefaultData }.isNull()
                    get("Currency picker is clickable") { currencyPickerIsClickable }.isTrue()

                    get("Account deletion button is visible") {
                        accountDeletionButtonIsVisible
                    }.isFalse()

                    get("Currency choice state") {
                        currencyChoiceState
                    }.isEqualTo(currencyChoiceStore.state)

                    get("Currency choice state") {
                        currencyChoiceState
                    }.isEqualTo(currencyChoiceStore.state)
                }
            }

            When("Some balance is entered") {
                moneyAccountStore.dispatch(MoneyAccountIntent.EnterBalance("10"))
            }

            Then("State is updated but saving button is still not enabled") {
                expectThat(moneyAccountStore.state) {
                    get("Balance") { balance }.isEqualTo(BigDecimal(10))
                    get("Saving button is enabled") { savingButtonIsEnabled }.isFalse()
                }
            }

            lateinit var chosenCurrencyCell: CurrencyCell

            When("Currency is changed but saving button is still not enabled") {
                chosenCurrencyCell = moneyAccountStore.state
                    .currentCells
                    .filterIsInstance<CurrencyCell>()
                    .first { it.currency != moneyAccountStore.state.chosenCurrency }

                moneyAccountStore.dispatch(
                    CurrencyChoiceIntent.ClickOnCurrencyCell(chosenCurrencyCell)
                )
            }

            Then("Cell became chosen but saving button is still not enabled") {
                expectThat(moneyAccountStore.state) {
                    get("Saving button is enabled") { savingButtonIsEnabled }.isFalse()
                    get("Chosen currency") { chosenCurrency }.isEqualTo(chosenCurrencyCell.currency)
                }
            }

            When("Some account name is entered") {
                moneyAccountStore.dispatch(MoneyAccountIntent.EnterAccountName("Name"))
            }

            Then("State is updated but saving button became enabled") {
                expectThat(moneyAccountStore.state) {
                    get("Money account name") { moneyAccountName }.isEqualTo("Name")
                    get("Saving button is enabled") { savingButtonIsEnabled }.isTrue()
                }
            }

            val accountCreationParamsSlot = slot<MoneyAccountCreationParams>()

            When("Click on saving button") {
                coEvery {
                    storeParams.createMoneyAccountUseCase.execute(capture(accountCreationParamsSlot))
                } just runs
                moneyAccountStore.dispatch(MoneyAccountIntent.ClickOnSaveButton)
            }

            Then("Transaction is created and screen is closed") {
                coVerify { storeParams.createMoneyAccountUseCase.execute(any()) }

                expectThat(accountCreationParamsSlot.captured) {
                    get { currency }.isEqualTo(moneyAccountStore.state.chosenCurrency)
                    get { initialBalance }.isEqualTo(moneyAccountStore.state.balance)
                    get { accountName }.isEqualTo(moneyAccountStore.state.moneyAccountName)
                }

                expectThat(moneyAccountStore.capturedEvents.takeLast(2)) {
                    get { containsType<MoneyAccountEvent.ShowMessage>() }.isTrue()
                    get { containsType<MoneyAccountEvent.CloseScreen>() }.isTrue()
                }
            }

        }

        Scenario("Money account editing") {

            // region Given data.

            val accountCurrency = "RUB"
            val accountName = "account_name"
            val accountBalance = BigDecimal(550)
            val accountId = Id.existing("account_id")

            val account = MoneyAccount(
                id = accountId,
                name = accountName,
                isFavorite = false,
                currencyCode = accountCurrency,
                ownerId = Id.existing("user_id")
            )

            // endregion


            val storeParams = MoneyAccountStoreCreator.create(
                MoneyAccountScreenArguments.Editing(
                    transitionName = EMPTY_STRING,
                    moneyAccountId = accountId
                )
            )
            val moneyAccountStore = storeParams.moneyAccountStore
            val currencyChoiceStore = storeParams.currencyChoiceStore


            Given("Account with name '$accountName', balance '$accountBalance' and '$accountCurrency' currency") {
                coEvery { storeParams.balanceCalculator.calculate(accountId) } answers {
                    accountBalance
                }
                every { storeParams.moneyAccountRepository.accountFlow(accountId) } answers {
                    flowOf(account)
                }
            }

            When("Store is started") {
                moneyAccountStore.start()
            }

            Then("Correct initial state is produced") {
                expectThat(moneyAccountStore.state) {
                    get("Balance") { balance }.isEqualTo(accountBalance)
                    get("Saving button is enabled") { savingButtonIsEnabled }.isFalse()
                    get("Money account name") { moneyAccountName }.isEqualTo(account.name)
                    get("Currency picker is clickable") { currencyPickerIsClickable }.isFalse()

                    get("Account deletion button is visible") {
                        accountDeletionButtonIsVisible
                    }.isTrue()

                    get("Money account default data") { moneyAccountDefaultData }.isEqualTo(
                        MoneyAccountDefaultData(accountBalance, accountName)
                    )

                    get("Currency choice state") {
                        currencyChoiceState
                    }.isEqualTo(currencyChoiceStore.state)

                    get("Chosen currency") {
                        chosenCurrency?.currencyCode
                    }.isEqualTo(accountCurrency)
                }
            }

            When("Modify account name") {
                moneyAccountStore.dispatch(MoneyAccountIntent.EnterAccountName("changed"))
            }

            Then("State is updated and saving button became enabled") {
                expectThat(moneyAccountStore.state) {
                    get("Money account name") { moneyAccountName }.isEqualTo("changed")
                    get("Saving button is enabled") { savingButtonIsEnabled }.isTrue()
                }
            }

            When("Revert account name") {
                moneyAccountStore.dispatch(MoneyAccountIntent.EnterAccountName(accountName))
            }

            Then("State is updated and saving button became disabled") {
                expectThat(moneyAccountStore.state) {
                    get("Money account name") { moneyAccountName }.isEqualTo(accountName)
                    get("Saving button is enabled") { savingButtonIsEnabled }.isFalse()
                }
            }

            When("Modify account balance") {
                moneyAccountStore.dispatch(MoneyAccountIntent.EnterBalance("100"))
            }

            Then("State is updated and saving button became enabled") {
                expectThat(moneyAccountStore.state) {
                    get("Balance") { balance }.isEqualTo(BigDecimal(100))
                    get("Saving button is enabled") { savingButtonIsEnabled }.isTrue()
                }
            }

            When("Revert account balance") {
                moneyAccountStore.dispatch(MoneyAccountIntent.EnterBalance(accountBalance.toString()))
            }

            Then("State is updated and saving button became disabled") {
                expectThat(moneyAccountStore.state) {
                    get("Balance") { balance }.isEqualTo(accountBalance)
                    get("Saving button is enabled") { savingButtonIsEnabled }.isFalse()
                }
            }

            When("Modify account balance") {
                moneyAccountStore.dispatch(MoneyAccountIntent.EnterBalance("50"))
            }

            Then("State is updated and saving button became enabled") {
                expectThat(moneyAccountStore.state) {
                    get("Balance") { balance }.isEqualTo(BigDecimal(50))
                    get("Saving button is enabled") { savingButtonIsEnabled }.isTrue()
                }
            }

            val accountEditingParamsSlot = slot<MoneyAccountEditingParams>()

            When("Click on saving button") {
                coEvery {
                    storeParams.editMoneyAccountUseCase.execute(capture(accountEditingParamsSlot))
                } just runs
                moneyAccountStore.dispatch(MoneyAccountIntent.ClickOnSaveButton)
            }

            Then("Transaction is edited and screen is closed") {
                coVerify { storeParams.editMoneyAccountUseCase.execute(any()) }

                expectThat(accountEditingParamsSlot.captured) {
                    get { moneyAccountId }.isEqualTo(accountId)
                    get { newBalance }.isEqualTo(moneyAccountStore.state.balance)
                    get { newName }.isEqualTo(moneyAccountStore.state.moneyAccountName)
                }

                expectThat(moneyAccountStore.capturedEvents.takeLast(2)) {
                    get { containsType<MoneyAccountEvent.ShowMessage>() }.isTrue()
                    get { containsType<MoneyAccountEvent.CloseScreen>() }.isTrue()
                }
            }

        }

        Scenario("Existing money account deletion") {

            // region Given data.

            val accountCurrency = "RUB"
            val accountName = "account_name"
            val accountBalance = BigDecimal(550)
            val accountId = Id.existing("account_id")

            val account = MoneyAccount(
                id = accountId,
                name = accountName,
                isFavorite = false,
                currencyCode = accountCurrency,
                ownerId = Id.existing("user_id")
            )

            // endregion


            val storeParams = MoneyAccountStoreCreator.create(
                MoneyAccountScreenArguments.Editing(
                    transitionName = EMPTY_STRING,
                    moneyAccountId = accountId
                )
            )
            val moneyAccountStore = storeParams.moneyAccountStore

            Given(
                "Store is started for account with " +
                        "name '$accountName', " +
                        "balance '$accountBalance' " +
                        "and '$accountCurrency' currency"
            ) {
                coEvery { storeParams.balanceCalculator.calculate(accountId) } answers {
                    accountBalance
                }
                every { storeParams.moneyAccountRepository.accountFlow(accountId) } answers {
                    flowOf(account)
                }
                moneyAccountStore.start()
            }

            When("Click on deletion button") {
                moneyAccountStore.dispatch(MoneyAccountIntent.ClickOnDeleteButton)
            }

            Then("Dialog showing event is produced") {
                expectThat(moneyAccountStore.lastEvent).isA<MoneyAccountEvent.ShowAccountDeletionDialog>()
            }

            When("Click on confirm deletion button") {
                coEvery { storeParams.deleteMoneyAccountUseCase.execute(accountId) } just runs
                moneyAccountStore.dispatch(MoneyAccountIntent.ClickOnConfirmAccountDeletion)
            }

            Then("Dialog showing event is produced") {
                coVerify { storeParams.deleteMoneyAccountUseCase.execute(accountId) }
                expectThat(moneyAccountStore.lastEvent).isA<MoneyAccountEvent.CloseScreen>()
            }

        }

    }

})