package serg.chuprin.finances.feature.dashboard.domain.builder

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
object DashboardBalanceWidgetBuilderTest : Spek({

    Feature("Dashboard balance widget builder") {

        Scenario("Building widget") {

            val transactionRepository = mockk<TransactionRepository>()
            val widgetBuilder = DashboardBalanceWidgetBuilder(transactionRepository)

            val currentUser = createTestUser()
            val currentPeriod = DataPeriod.from(currentUser.dataPeriodType)

            lateinit var widget: DashboardWidget.Balance
            lateinit var flow: Flow<DashboardWidget.Balance>

            every {
                transactionRepository.transactionsFlow(any())
            } returns flowOf(createTransactionsForScenario1(currentUser))

            When("Build method is called") {
                flow = widgetBuilder.build(
                    currentUser = currentUser,
                    currentPeriod = currentPeriod,
                )
            }

            Then("Builder returns flow of single widget") {
                val widgetsList = runBlocking { flow.toList() }
                expectThat(widgetsList.size).isEqualTo(1)
                widget = widgetsList.last()
            }

            And("Balance is valid") {
                expectThat(widget.balance).isEqualTo(BigDecimal(128))
            }

            And("Expenses amount is valid") {
                expectThat(widget.currentPeriodExpenses).isEqualTo(BigDecimal(50))
            }

            And("Incomes amount is valid") {
                expectThat(widget.currentPeriodIncomes).isEqualTo(BigDecimal(70))
            }

            And("Currency is the same as user's default") {
                expectThat(widget.currency).isEqualTo(currentUser.defaultCurrency)
            }

        }

    }

})

private fun createTransactionsForScenario1(user: User): List<Transaction> {
    val futureTransactionDate = LocalDateTime.now().plusMonths(2)
    val pastTransactionDate = LocalDateTime.now().minusMonths(2)
    return listOf(
        createTransaction(user, TransactionType.BALANCE, amount = "100"),
        createTransaction(user, TransactionType.PLAIN, amount = "-50"),
        createTransaction(user, TransactionType.PLAIN, amount = "70"),
        createTransaction(user, TransactionType.PLAIN, amount = "3", dateTime = pastTransactionDate),
        createTransaction(user, TransactionType.BALANCE, amount = "5", dateTime = pastTransactionDate),
        createTransaction(user, TransactionType.PLAIN, amount = "30", dateTime = futureTransactionDate),
        createTransaction(user, TransactionType.BALANCE, amount = "1", dateTime = futureTransactionDate)
    )
}

private fun createTransaction(
    user: User,
    type: TransactionType,
    dateTime: LocalDateTime = LocalDateTime.now(),
    amount: String
): Transaction {
    return Transaction(Id.createNew(), user.id, Id.createNew(), type, "USD", null, dateTime, amount)
}

private fun createTestUser(
    dataPeriodType: DataPeriodType = DataPeriodType.MONTH,
    currencyCode: String = "USD"
): User {
    return User(Id.createNew(), "email", "", "", dataPeriodType, currencyCode)
}