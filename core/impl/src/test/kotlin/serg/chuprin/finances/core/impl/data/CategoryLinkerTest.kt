package serg.chuprin.finances.core.impl.data

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.category.CategoryIdToCategory
import serg.chuprin.finances.core.impl.domain.linker.CategoryLinker
import strikt.api.expectThat
import strikt.assertions.getValue
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

/**
 * Created by Sergey Chuprin on 01.05.2020.
 */
object CategoryLinkerTest : Spek({

    Feature("Transaction category linker") {

        val linker = CategoryLinker()

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

            lateinit var categoryIdToCategory: CategoryIdToCategory

            When("Method is called") {
                categoryIdToCategory = linker.linkWithParents(categories)
            }

            Then("Map is valid") {
                expectThat(categoryIdToCategory) {
                    hasSize(categories.size)
                    getValue(category1.id)
                        .isEqualTo(CategoryWithParent(category1, null))

                    getValue(parentCategory2.id)
                        .isEqualTo(CategoryWithParent(parentCategory2, null))

                    getValue(parentCategory3.id)
                        .isEqualTo(CategoryWithParent(parentCategory3, null))

                    getValue(category2.id)
                        .isEqualTo(CategoryWithParent(category2, parentCategory2))

                    getValue(category3.id)
                        .isEqualTo(CategoryWithParent(category3, parentCategory3))
                }
            }

        }

    }

})

private fun createCategory(id: Id, parentCategoryId: Id?): Category {
    return Category(
        id = id,
        name = "",
        colorHex = "",
        ownerId = Id.UNKNOWN,
        parentCategoryId = parentCategoryId,
        type = CategoryType.INCOME
    )
}