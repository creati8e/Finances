package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import io.mockk.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.extensions.TimberConsoleTree
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.domain.OnboardingMoneyAccountCreationParams
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.factory.AccountsSetupOnboardingStoreTestFactory
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingState
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.state.AccountsSetupOnboardingStepState
import strikt.api.Assertion
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import timber.log.Timber
import java.util.*

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
object AccountsSetupOnboardingStoreTest : Spek({

    Feature("Accounts setup onboarding store") {

        Timber.plant(TimberConsoleTree())

        Scenario("Starting store") {

            val params = AccountsSetupOnboardingStoreTestFactory.build()
            val store = params.store

            val userCurrency = Currency.getInstance("USD")

            Given("User's currency is USD") {
                params.userRepository.mockCurrentUser(userCurrency.currencyCode)
            }

            When("Starting store") {
                store.start()
            }

            Then("Initial step is cash question") {
                store.state.assertCurrentStepIs<AccountsSetupOnboardingStepState.CashQuestion>()
            }

            And("Balances are nulls") {
                expectThat(store.state) {
                    get { cashBalance }.isNull()
                    get { bankCardBalance }.isNull()
                }
            }

            And("Currency is the same as user's") {
                expectThat(store.state.currency).isEqualTo(userCurrency)
            }

        }


        Scenario("User answered positively on cash question") {

            val params = AccountsSetupOnboardingStoreTestFactory.build()
            val store = params.store

            Given("Store is started") {
                params.userRepository.mockCurrentUser()
                store.start()
            }

            When("User clicked on positive button") {
                store.dispatch(AccountsSetupOnboardingIntent.ClickOnPositiveButton)
            }

            Then("Current step changed to cash balance entering") {
                store.state.assertCurrentStepIs<AccountsSetupOnboardingStepState.CashBalanceEnter>()
            }

        }

        Scenario("User answered negatively on cash question") {

            val params = AccountsSetupOnboardingStoreTestFactory.build()
            val store = params.store

            Given("Store is started") {
                params.userRepository.mockCurrentUser()
                store.start()
            }

            When("User clicked on negative button") {
                store.dispatch(AccountsSetupOnboardingIntent.ClickOnNegativeButton)
            }

            Then("Current step changed to cash balance entering") {
                store.state.assertCurrentStepIs<AccountsSetupOnboardingStepState.BankCardQuestion>()
            }

        }

        Scenario("User answered positively on bank card question") {

            val params = AccountsSetupOnboardingStoreTestFactory.build()
            val store = params.store

            val bankCardEnteredBalance = "10"
            val bankCardAccountName = "bank_card"
            val bankCardBalanceBigDecimal = bankCardEnteredBalance.toBigDecimal()
            val bankAccountCardParams = OnboardingMoneyAccountCreationParams(
                accountName = bankCardAccountName,
                balance = bankCardBalanceBigDecimal
            )

            val cashEnteredBalance = "5"
            val cashAccountName = "cash"
            val cashEnteredBalanceBigDecimal = cashEnteredBalance.toBigDecimal()
            val cashAccountCardParams = OnboardingMoneyAccountCreationParams(
                accountName = cashAccountName,
                balance = cashEnteredBalanceBigDecimal
            )

            val finalStepMessage = "final_step_message"

            Given("Current step is bank card question") {
                params.userRepository.mockCurrentUser()
                with(store) {
                    start()

                    with(params) {
                        every {
                            amountFormatter.formatInput(cashEnteredBalance, any())
                        } returns cashEnteredBalance

                        every {
                            amountParser.parse(cashEnteredBalance)
                        } returns cashEnteredBalanceBigDecimal
                    }

                    dispatch(AccountsSetupOnboardingIntent.ClickOnPositiveButton)
                    dispatch(AccountsSetupOnboardingIntent.EnterBalance(cashEnteredBalance))
                    dispatch(AccountsSetupOnboardingIntent.ClickOnAcceptBalanceButton)

                    state.assertCurrentStepIs<AccountsSetupOnboardingStepState.BankCardQuestion>()
                    expectThat(state.cashBalance).isEqualTo(cashEnteredBalanceBigDecimal)
                }
            }

            When("User clicked on positive button") {
                store.dispatch(AccountsSetupOnboardingIntent.ClickOnPositiveButton)
            }

            Then("Current step changed to bank card balance entering") {
                store.state.assertCurrentStepIs<AccountsSetupOnboardingStepState.BankCardBalanceEnter>()
            }

            When("Some balance is entered and accepted") {
                with(params) {
                    coEvery {
                        completeOnboardingUseCase.execute(
                            cashAccountParams = cashAccountCardParams,
                            bankAccountCardParams = bankAccountCardParams
                        )
                    } just runs

                    every {
                        resourceManager.getString(R.string.bank_card_money_account_default_name)
                    } returns bankCardAccountName
                    every {
                        resourceManager.getString(R.string.cash_money_account_default_name)
                    } returns cashAccountName

                    every {
                        resourceManager.getString(
                            R.string.onboarding_accounts_setup_subtitle_setup_finished
                        )
                    } returns finalStepMessage

                    every {
                        amountFormatter.formatInput(bankCardEnteredBalance, any())
                    } returns bankCardEnteredBalance

                    every {
                        amountParser.parse(bankCardEnteredBalance)
                    } returns bankCardBalanceBigDecimal
                }

                with(store) {
                    dispatch(AccountsSetupOnboardingIntent.EnterBalance(bankCardEnteredBalance))
                    dispatch(AccountsSetupOnboardingIntent.ClickOnAcceptBalanceButton)
                }
            }

            Then("Onboarding have been completed with entered balance") {
                with(params) {
                    coVerify {
                        completeOnboardingUseCase.execute(
                            cashAccountParams = cashAccountCardParams,
                            bankAccountCardParams = bankAccountCardParams
                        )
                    }
                }
            }

            Then("State contains this balance in property") {
                expectThat(store.state) {
                    get { cashBalance }.isEqualTo(cashEnteredBalanceBigDecimal)
                    get { bankCardBalance }.isEqualTo(bankCardBalanceBigDecimal)
                }
            }

            And("Current step is final") {
                expectThat(store.state.stepState)
                    .isA<AccountsSetupOnboardingStepState.EverythingIsSetUp>()
                    .and {
                        get { message }
                            .isEqualTo(finalStepMessage)
                    }
            }

        }

    }

})

private inline fun <reified S> AccountsSetupOnboardingState.assertCurrentStepIs(): Assertion.Builder<S> {
    return expectThat(stepState).isA()
}

private fun UserRepository.mockCurrentUser(currencyCode: String = "USD") {
    coEvery { getCurrentUser() } returns User(
        Id("id"),
        "email",
        "url",
        "name",
        DataPeriodType.MONTH,
        currencyCode
    )
}