package serg.chuprin.finances.core.test.presentation.formatter

import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.presentation.formatter.CategoryWithParentFormatter

/**
 * Created by Sergey Chuprin on 11.01.2021.
 */
class CategoryWithParentFormatterTestImpl : CategoryWithParentFormatter {

    override fun format(
        categoryWithParent: CategoryWithParent?,
        transaction: Transaction?
    ): String {
        if (transaction?.isBalance == true) {
            return "Balance correction"
        }
        if (categoryWithParent == null) {
            return "No category"
        }
        return listOfNotNull(
            categoryWithParent.parentCategory,
            categoryWithParent.category
        ).joinToString(separator = " / ", transform = Category::name)
    }

}