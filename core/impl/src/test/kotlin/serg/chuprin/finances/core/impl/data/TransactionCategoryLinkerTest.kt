package serg.chuprin.finances.core.impl.data

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import strikt.api.expectThat
import strikt.assertions.getValue
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

/**
 * Created by Sergey Chuprin on 01.05.2020.
 */
object TransactionCategoryLinkerTest : Spek({

    Feature("Transaction category linker") {

        val linker = TransactionCategoryLinker()

        Scenario("Linking categories with parents") {

            val category1 = createCategory(Id("category1"), null)
            val parentCategory2 = createCategory(Id("parentCategory2"), null)
            val parentCategory3 = createCategory(Id("parentCategory3"), null)
            val category2 = createCategory(Id("category2"), parentCategory2.id)
            val category3 = createCategory(Id("category3"), parentCategory3.id)

            val categories = listOf(
                category1,
                parentCategory2,
                parentCategory3,
                category2,
                category3
            )

            lateinit var linked: Map<Id, TransactionCategoryWithParent>

            When("Method is called") {
                linked = linker.linkWithParents(categories)
            }

            Then("Map is valid") {
                expectThat(linked) {
                    hasSize(categories.size)
                    getValue(category1.id)
                        .isEqualTo(TransactionCategoryWithParent(category1, null))

                    getValue(parentCategory2.id)
                        .isEqualTo(TransactionCategoryWithParent(parentCategory2, null))

                    getValue(parentCategory3.id)
                        .isEqualTo(TransactionCategoryWithParent(parentCategory3, null))

                    getValue(category2.id)
                        .isEqualTo(TransactionCategoryWithParent(category2, parentCategory2))

                    getValue(category3.id)
                        .isEqualTo(TransactionCategoryWithParent(category3, parentCategory3))
                }
            }

        }

    }

})

private fun createCategory(id: Id, parentCategoryId: Id?): TransactionCategory {
    return TransactionCategory(
        id = id,
        name = "",
        colorHex = "",
        ownerId = Id.UNKNOWN,
        parentCategoryId = parentCategoryId,
        type = TransactionCategoryType.INCOME
    )
}