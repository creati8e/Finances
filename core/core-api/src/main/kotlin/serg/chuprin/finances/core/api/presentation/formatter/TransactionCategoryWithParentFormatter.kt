package serg.chuprin.finances.core.api.presentation.formatter

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
interface TransactionCategoryWithParentFormatter {

    /**
     * @return pair of parent category and child category name.
     */
    fun format(
        transactionCategoryWithParent: TransactionCategoryWithParent?,
        transaction: Transaction
    ): Pair<String, String>

}