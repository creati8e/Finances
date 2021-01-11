package serg.chuprin.finances.feature.transaction.domain.usecase

import io.mockk.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.extensions.startOfDay
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 11.01.2021.
 */
object CreateTransactionUseCaseTest : Spek({

    Feature("Create transaction use case") {

        val transactionRepository = mockk<TransactionRepository>()
        val useCase = CreateTransactionUseCase(transactionRepository)

        val createdTransactionSlot = slot<List<Transaction>>()
        every { transactionRepository.createOrUpdate(capture(createdTransactionSlot)) } just runs

        beforeEachScenario {
            createdTransactionSlot.clear()
        }

        Scenario("Create expense transaction with today date") {

            val amount = BigDecimal.ONE
            val userId = Id.existing("1")
            val todayDateTime = LocalDateTime.now().withNano(0).withSecond(0)

            When("Execute use case") {
                useCase.execute(
                    ownerId = userId,
                    amount = amount,
                    category = null,
                    date = todayDateTime.toLocalDate(),
                    moneyAccount = MoneyAccount.EMPTY,
                    operation = TransactionChosenOperation.Plain(PlainTransactionType.EXPENSE)
                )
            }

            Then("Created transaction is valid") {
                expectThat(createdTransactionSlot.captured.first()) {
                    get { dateTime }
                        .describedAs("Transaction datetime")
                        .isEqualTo(todayDateTime)

                    get { isExpense }
                        .describedAs("Is expense transaction")
                        .isTrue()

                    get { type }
                        .describedAs("Transaction type")
                        .isEqualTo(TransactionType.PLAIN)

                    get { ownerId }
                        .describedAs("Owner id")
                        .isEqualTo(userId)

                    get { amount }
                        .describedAs("Transaction's amount")
                        .isEqualTo(amount)
                }
            }

        }

        Scenario("Create income transaction with yesterday date") {

            val userId = Id.existing("1")
            val yesterdayDateTime = LocalDateTime.now().minusDays(1).withNano(0).withSecond(0)

            When("Execute use case") {
                useCase.execute(
                    ownerId = userId,
                    category = null,
                    amount = BigDecimal.ONE,
                    moneyAccount = MoneyAccount.EMPTY,
                    date = yesterdayDateTime.toLocalDate(),
                    operation = TransactionChosenOperation.Plain(PlainTransactionType.INCOME)
                )
            }

            Then("Created transaction is valid") {
                expectThat(createdTransactionSlot.captured.first()) {
                    get { dateTime }
                        .describedAs("Transaction datetime")
                        .isEqualTo(yesterdayDateTime.startOfDay())

                    get { isIncome }
                        .describedAs("Is income transaction")
                        .isTrue()

                    get { type }
                        .describedAs("Transaction type")
                        .isEqualTo(TransactionType.PLAIN)

                    get { ownerId }
                        .describedAs("Owner id")
                        .isEqualTo(userId)
                }
            }

        }

    }

})