package serg.chuprin.finances.core.api.domain.linker

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
interface TransactionWithCategoriesLinker {

    /**
     * @return map of parent categories associated with transactions.
     * Transactions with child categories are associated with their's categories parents.
     */
    fun linkCategoryParentsWithTransactions(
        transactions: List<Transaction>,
        categoryWithParentMap: Map<Id, TransactionCategoryWithParent>
    ): Map<TransactionCategory?, List<Transaction>>

    /**
     * @return map of transactions associated with their categories
     * ([TransactionCategoryWithParent]] also includes category's parent).
     */
    fun linkTransactionsWithCategories(
        transactions: List<Transaction>,
        categoryWithParentMap: Map<Id, TransactionCategoryWithParent>
    ): Map<Transaction, TransactionCategoryWithParent?>

}