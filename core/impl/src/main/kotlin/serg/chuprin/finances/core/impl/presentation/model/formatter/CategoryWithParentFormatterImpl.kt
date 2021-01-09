package serg.chuprin.finances.core.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.presentation.formatter.CategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
internal class CategoryWithParentFormatterImpl @Inject constructor(
    private val resourceManger: ResourceManger
) : CategoryWithParentFormatter {

    override fun format(
        categoryWithParent: CategoryWithParent?,
        transaction: Transaction?
    ): String {
        if (transaction?.isBalance == true) {
            return resourceManger.getString(R.string.balance_correction_transaction)
        }
        if (categoryWithParent == null) {
            return resourceManger.getString(R.string.no_category)
        }
        return listOfNotNull(
            categoryWithParent.parentCategory,
            categoryWithParent.category
        ).joinToString(separator = " / ", transform = Category::name)

    }

}