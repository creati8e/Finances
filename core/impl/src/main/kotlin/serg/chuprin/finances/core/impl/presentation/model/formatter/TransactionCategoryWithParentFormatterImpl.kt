package serg.chuprin.finances.core.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.presentation.formatter.TransactionCategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
internal class TransactionCategoryWithParentFormatterImpl @Inject constructor(
    private val resourceManger: ResourceManger
) : TransactionCategoryWithParentFormatter {

    override fun format(
        categoryWithParent: TransactionCategoryWithParent?,
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
        ).joinToString(separator = " / ", transform = TransactionCategory::name)

    }

}