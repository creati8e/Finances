package serg.chuprin.finances.core.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
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
        transactionCategoryWithParent: TransactionCategoryWithParent?,
        transaction: Transaction
    ): Pair<String, String> {
        if (transaction.isBalance) {
            return resourceManger.getString(R.string.balance_correction_transaction) to EMPTY_STRING
        }
        val (category, parentCategory) = transactionCategoryWithParent
            ?: return resourceManger.getString(R.string.no_category) to EMPTY_STRING
        return when {
            parentCategory != null -> {
                parentCategory.name to category.name
            }
            else -> category.name to EMPTY_STRING
        }
    }

}