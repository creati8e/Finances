package serg.chuprin.finances.core.impl.data

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.05.2020.
 */
internal class TransactionWithCategoriesLinker @Inject constructor() {

    /**
     * @return map of parent categories associated with transactions.
     * Transactions with child categories are associated with their's categories parents.
     */
    fun linkCategoryParentsWithTransactions(
        transactions: List<Transaction>,
        categoryWithParentMap: Map<Id, TransactionCategoryWithParent>
    ): Map<TransactionCategory?, List<Transaction>> {
        return transactions.groupBy { transaction ->
            categoryWithParentMap[transaction.categoryId]?.run {
                parentCategory ?: category
            }
        }
    }

    /**
     * @return map of transactions associated with their categories
     * ([TransactionCategoryWithParent]] also includes category's parent).
     */
    fun linkTransactionsWithCategories(
        transactions: List<Transaction>,
        categoryWithParentMap: Map<Id, TransactionCategoryWithParent>
    ): Map<Transaction, TransactionCategoryWithParent?> {
        return transactions.associateBy(
            { transaction -> transaction },
            { transaction -> categoryWithParentMap[transaction.categoryId] }
        )
    }

}