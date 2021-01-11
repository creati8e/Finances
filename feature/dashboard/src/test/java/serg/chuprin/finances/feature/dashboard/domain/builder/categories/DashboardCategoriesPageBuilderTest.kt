package serg.chuprin.finances.feature.dashboard.domain.builder.categories

import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.CategoryToTransactionsList
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.test.domain.TransactionAmountCalculatorTestImpl
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardCategoriesWidgetPage
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 01.05.2020.
 * For other categories amount is calculated too.
 */
object DashboardCategoriesPageBuilderTest : Spek({

    Feature("Dashboard categories page builder") {

        val builder = DashboardCategoriesPageBuilder(TransactionAmountCalculatorTestImpl())

        Scenario("Build pages") {

            val topCategoriesCount = 2

            // Transaction ids are not important here.
            val incomeCategory1 = createCategory(Id("incomeCategory1"))
            val incomeCategory2 = createCategory(Id("incomeCategory2"))

            val incomeCategoryToTransactionsList = CategoryToTransactionsList(
                mapOf(
                    incomeCategory1 to listOf(
                        createTransaction("500"),
                        createTransaction("1000")
                    ),
                    incomeCategory2 to listOf(
                        createTransaction("90"),
                        createTransaction("90")
                    ),
                    createCategory(Id("incomeCategory3")) to listOf(
                        createTransaction("100"),
                        createTransaction("50")
                    ),
                    createCategory(Id("incomeCategory4")) to listOf(
                        createTransaction("20"),
                        createTransaction("10")
                    )
                )
            )

            val expenseCategory1 = createCategory(Id("expenseCategory1"))
            val expenseCategory2 = createCategory(Id("expenseCategory2"))

            val expenseCategoryToTransactionsList = CategoryToTransactionsList(
                mapOf(
                    expenseCategory1 to listOf(
                        createTransaction("-40"),
                        createTransaction("-60")
                    ),
                    expenseCategory2 to listOf(
                        createTransaction("-20"),
                        createTransaction("-10")
                    ),
                    createCategory(Id("expenseCategory3")) to listOf(
                        createTransaction("-10"),
                        createTransaction("-5")
                    )
                )
            )

            lateinit var incomeCategoriesPage: DashboardCategoriesWidgetPage
            lateinit var expenseCategoriesPage: DashboardCategoriesWidgetPage

            When("Method is called for income categories") {
                incomeCategoriesPage = runBlocking {
                    builder.build(
                        PlainTransactionType.INCOME,
                        incomeCategoryToTransactionsList,
                        topCategoriesCount
                    )
                }
            }

            Then("Page has valid total amount") {
                expectThat(incomeCategoriesPage.totalAmount).isEqualTo("1860".toBigDecimal())
            }

            And("Page has valid 'other' amount") {
                expectThat(incomeCategoriesPage.otherCategoriesShare).isEqualTo("180".toBigDecimal())
            }

            And("Page has valid category amounts") {
                expectThat(incomeCategoriesPage.categoryShares)
                    .containsExactlyInAnyOrder(
                        listOf(
                            incomeCategory2 to "180".toBigDecimal(),
                            incomeCategory1 to "1500".toBigDecimal()
                        )
                    )
            }

            When("Method is called for expense categories") {
                expenseCategoriesPage = runBlocking {
                    builder.build(
                        PlainTransactionType.EXPENSE,
                        expenseCategoryToTransactionsList,
                        topCategoriesCount
                    )
                }
            }
            And("Page has valid total amount") {
                expectThat(expenseCategoriesPage.totalAmount).isEqualTo("145".toBigDecimal())
            }

            And("Page has valid 'other' amount") {
                expectThat(expenseCategoriesPage.otherCategoriesShare).isEqualTo("15".toBigDecimal())
            }

            And("Page has valid category amounts") {
                expectThat(expenseCategoriesPage.categoryShares)
                    .containsExactlyInAnyOrder(
                        listOf(
                            expenseCategory1 to "100".toBigDecimal(),
                            expenseCategory2 to "30".toBigDecimal()
                        )
                    )
            }

        }

    }

})

private fun createTransaction(amount: String): Transaction {
    return Transaction(
        id = Id.UNKNOWN,
        ownerId = Id.UNKNOWN,
        moneyAccountId = Id.UNKNOWN,
        type = TransactionType.PLAIN,
        currencyCode = "USD",
        categoryId = null,
        _dateTime = LocalDateTime.now(),
        _amount = amount
    )
}

private fun createCategory(id: Id): Category {
    return Category(
        id = id,
        name = "",
        colorHex = "",
        ownerId = Id.UNKNOWN,
        parentCategoryId = null,
        type = CategoryType.INCOME
    )
}