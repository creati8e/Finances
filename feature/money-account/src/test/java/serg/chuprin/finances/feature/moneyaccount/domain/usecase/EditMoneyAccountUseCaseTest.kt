package serg.chuprin.finances.feature.moneyaccount.domain.usecase

import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.MoneyAccountBalanceCalculator
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.moneyaccount.domain.model.MoneyAccountEditingParams
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 18.01.2021.
 */
object EditMoneyAccountUseCaseTest : Spek({

    Feature("Edit money account use case") {

        val transactionRepository = mockk<TransactionRepository>()
        val moneyAccountRepository = mockk<MoneyAccountRepository>()
        val moneyAccountBalanceCalculator = mockk<MoneyAccountBalanceCalculator>()

        val useCase = EditMoneyAccountUseCase(
            transactionRepository,
            moneyAccountRepository,
            moneyAccountBalanceCalculator
        )

        beforeEachScenario {
            unmockkAll()
        }

        Scenario("Edit account and negate balance") {

            var exception: Throwable? = null

            When("Execute use case") {
                exception = runCatching {
                    runBlockingTest {
                        useCase.execute(
                            MoneyAccountEditingParams(
                                newName = "accountName",
                                moneyAccountId = Id.existing("1"),
                                newBalance = BigDecimal.ONE.negate()
                            )
                        )
                    }
                }.exceptionOrNull()
            }

            Then("An exception was thrown") {
                expectThat(exception).isNotNull()
            }

        }

        Scenario("Edit account when there's nothing changed") {

            val accountName = "account"
            val accountBalance = BigDecimal(50)

            val moneyAccount = MoneyAccount(
                name = accountName,
                isFavorite = false,
                id = Id.createNew(),
                currencyCode = "USD",
                ownerId = Id.createNew()
            )

            every { moneyAccountRepository.accountFlow(moneyAccount.id) } answers {
                flowOf(moneyAccount)
            }

            coEvery { moneyAccountBalanceCalculator.calculate(moneyAccount.id) } answers {
                accountBalance
            }

            When("Execute use case") {
                runBlockingTest {
                    useCase.execute(
                        MoneyAccountEditingParams(
                            moneyAccountId = moneyAccount.id,
                            newName = accountName,
                            newBalance = accountBalance
                        )
                    )
                }
            }

            Then("Account is not modified") {
                verify(exactly = 0, verifyBlock = {
                    moneyAccountRepository.createOrUpdateAccount(any())
                })
            }

            And("Balance is not modified") {
                verify(exactly = 0, verifyBlock = {
                    transactionRepository.createOrUpdate(any())
                })
            }

        }

        Scenario("Edit account when account name and balance are changed") {

            val accountName = "account"
            val accountBalance = BigDecimal(50)

            val accountNewName = "account1"
            val accountNewBalance = BigDecimal(150)

            val moneyAccount = MoneyAccount(
                name = accountName,
                isFavorite = false,
                id = Id.createNew(),
                currencyCode = "USD",
                ownerId = Id.createNew()
            )

            every { moneyAccountRepository.accountFlow(moneyAccount.id) } answers {
                flowOf(moneyAccount)
            }

            coEvery { moneyAccountBalanceCalculator.calculate(moneyAccount.id) } answers {
                accountBalance
            }

            val accountSlot = slot<MoneyAccount>()
            every { moneyAccountRepository.createOrUpdateAccount(capture(accountSlot)) } just runs

            val transactionSlot = slot<Collection<Transaction>>()
            every { transactionRepository.createOrUpdate(capture(transactionSlot)) } just runs

            When("Execute use case") {
                runBlockingTest {
                    useCase.execute(
                        MoneyAccountEditingParams(
                            moneyAccountId = moneyAccount.id,
                            newName = accountNewName,
                            newBalance = accountNewBalance
                        )
                    )
                }
            }

            Then("Account is modified") {
                expectThat(accountSlot.captured.name).isEqualTo(accountNewName)
            }

            And("Balance is modified") {
                expectThat(transactionSlot.captured.singleOrNull())
                    .isNotNull()
                    .and {
                        get("Transaction type") { type }.isEqualTo(TransactionType.BALANCE)
                    }
                    .and {
                        val expectedTransactionAmount = accountNewBalance - accountBalance
                        get("Amount") { amount }.isEqualTo(expectedTransactionAmount)
                    }
            }

        }

    }

})