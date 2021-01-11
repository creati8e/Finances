package serg.chuprin.finances.core.impl.data

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.CategoryToTransactionsList
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.impl.domain.linker.CategoryLinker
import serg.chuprin.finances.core.impl.domain.linker.TransactionWithCategoriesLinkerImpl
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 01.05.2020.
 */
object TransactionWithCategoriesLinkerTest : Spek({

    Feature("Transaction with categories linker") {

        val categoryLinker = CategoryLinker()
        val transactionWithCategoriesLinkerImpl = TransactionWithCategoriesLinkerImpl()

        Scenario("Link category parents with transactions") {

            val category2 = createCategory(Id("category2"))
            val parentCategory1 = createCategory(Id("parentCategory1"))
            val parentCategory3 = createCategory(Id("parentCategory3"))

            val categories = listOf(
                createCategory(Id("category1"), Id("parentCategory1")),
                category2,
                createCategory(Id("category3"), Id("parentCategory3")),
                parentCategory1,
                parentCategory3
            )

            val expectedCategoryToTransactionsList = CategoryToTransactionsList(
                mapOf(
                    null to listOf(
                        createTransaction(Id("transaction3")),
                        createTransaction(Id("transaction5"))
                    ),
                    parentCategory1 to listOf(
                        createTransaction(Id("transaction1"), Id("category1")),
                        createTransaction(Id("transaction6"), Id("parentCategory1"))
                    ),
                    category2 to listOf(
                        createTransaction(Id("transaction2"), Id("category2"))
                    ),
                    parentCategory3 to listOf(
                        createTransaction(Id("transaction4"), Id("parentCategory3"))
                    )
                )
            )

            lateinit var categoryToTransactionsList: CategoryToTransactionsList

            When("Method is called") {
                categoryToTransactionsList = transactionWithCategoriesLinkerImpl
                    .linkCategoryParentsWithTransactions(
                        expectedCategoryToTransactionsList.flatMap { (_, transactions) -> transactions },
                        categoryLinker.linkWithParents(categories)
                    )
            }

            Then("Created map is valid") {
                expectThat(categoryToTransactionsList) {
                    isEqualTo(expectedCategoryToTransactionsList)
                }
            }

        }

    }

})

private fun createTransaction(id: Id, categoryId: Id? = null): Transaction {
    return Transaction(
        id = id,
        ownerId = Id.createNew(),
        moneyAccountId = Id.createNew(),
        type = TransactionType.PLAIN,
        currencyCode = "USD",
        categoryId = categoryId,
        _dateTime = LocalDateTime.now(),
        _amount = "100"
    )
}

private fun createCategory(id: Id, parentCategoryId: Id? = null): Category {
    return Category(
        id = id,
        name = "",
        colorHex = "",
        ownerId = Id.UNKNOWN,
        parentCategoryId = parentCategoryId,
        type = CategoryType.INCOME
    )
}