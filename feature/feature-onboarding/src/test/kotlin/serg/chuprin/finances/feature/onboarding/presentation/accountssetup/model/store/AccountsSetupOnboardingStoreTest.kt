package serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store

import io.mockk.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.test.utils.TimberTestTree
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.domain.OnboardingMoneyAccountCreationParams
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.model.store.factory.AccountsSetupOnboardingStoreFactory
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

        Timber.plant(TimberTestTree())

        Scenario("Starting store") {

            val params = AccountsSetupOnboardingStoreFactory.build()
            val store = params.store

            val userCurrency = Currency.getInstance("USD")

            Given("User's currency is USD") {
                params.userRepository.mockCurrentUser(userCurrency.currencyCode)
            }

            When("Starting store") {
                store.testStart()
            }

            Then("Initial step is cash question") {
                store.state.assertCurrentStepIs<AccountsSetupOnboardingStepState.CashQuestion>()
            }

            And("Amounts are nulls") {
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

            val params = AccountsSetupOnboardingStoreFactory.build()
            val store = params.store

            Given("Store is started") {
                params.userRepository.mockCurrentUser()
                store.testStart()
            }

            When("User clicked on positive button") {
                store.dispatch(AccountsSetupOnboardingIntent.ClickOnPositiveButton)
            }

            Then("Current step changed to cash amount entering") {
                store.state.assertCurrentStepIs<AccountsSetupOnboardingStepState.CashAmountEnter>()
            }

        }

        Scenario("User answered negatively on cash question") {

            val params = AccountsSetupOnboardingStoreFactory.build()
            val store = params.store

            Given("Store is started") {
                params.userRepository.mockCurrentUser()
                store.testStart()
            }

            When("User clicked on negative button") {
                store.dispatch(AccountsSetupOnboardingIntent.ClickOnNegativeButton)
            }

            Then("Current step changed to cash amount entering") {
                store.state.assertCurrentStepIs<AccountsSetupOnboardingStepState.BankCardQuestion>()
            }

        }

        Scenario("User answered positively on bank card question") {

            val params = AccountsSetupOnboardingStoreFactory.build()
            val store = params.store

            val bankCardEnteredAmount = "10"
            val bankCardAccountName = "bank_card"
            val bankCardAmountBigDecimal = bankCardEnteredAmount.toBigDecimal()
            val bankAccountCardParams = OnboardingMoneyAccountCreationParams(
                accountName = bankCardAccountName,
                balance = bankCardAmountBigDecimal
            )

            val cashEnteredAmount = "5"
            val cashAccountName = "cash"
            val cashEnteredAmountBigDecimal = cashEnteredAmount.toBigDecimal()
            val cashAccountCardParams = OnboardingMoneyAccountCreationParams(
                accountName = cashAccountName,
                balance = cashEnteredAmountBigDecimal
            )

            val finalStepMessage = "final_step_message"

            Given("Current step is bank card question") {
                params.userRepository.mockCurrentUser()
                with(store) {
                    testStart()

                    with(params) {
                        every {
                            amountFormatter.formatInput(cashEnteredAmount, any())
                        } returns cashEnteredAmount

                        every {
                            amountParser.parse(cashEnteredAmount)
                        } returns cashEnteredAmountBigDecimal
                    }

                    dispatch(AccountsSetupOnboardingIntent.ClickOnPositiveButton)
                    dispatch(AccountsSetupOnboardingIntent.InputAmount(cashEnteredAmount))
                    dispatch(AccountsSetupOnboardingIntent.ClickOnAcceptBalanceButton)

                    state.assertCurrentStepIs<AccountsSetupOnboardingStepState.BankCardQuestion>()
                    expectThat(state.cashBalance).isEqualTo(cashEnteredAmountBigDecimal)
                }
            }

            When("User clicked on positive button") {
                store.dispatch(AccountsSetupOnboardingIntent.ClickOnPositiveButton)
            }

            Then("Current step changed to bank card amount entering") {
                store.state.assertCurrentStepIs<AccountsSetupOnboardingStepState.BankCardAmountEnter>()
            }

            When("Some amount is entered and accepted") {
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
                        amountFormatter.formatInput(bankCardEnteredAmount, any())
                    } returns bankCardEnteredAmount

                    every {
                        amountParser.parse(bankCardEnteredAmount)
                    } returns bankCardAmountBigDecimal
                }

                with(store) {
                    dispatch(AccountsSetupOnboardingIntent.InputAmount(bankCardEnteredAmount))
                    dispatch(AccountsSetupOnboardingIntent.ClickOnAcceptBalanceButton)
                }
            }

            Then("Onboarding have been completed with entered amount") {
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
                    get { cashBalance }.isEqualTo(cashEnteredAmountBigDecimal)
                    get { bankCardBalance }.isEqualTo(bankCardAmountBigDecimal)
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