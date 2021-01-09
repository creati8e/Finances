package serg.chuprin.finances.core.api.domain.linker

import serg.chuprin.finances.core.api.domain.model.TransactionToCategory
import serg.chuprin.finances.core.api.domain.model.CategoryToTransactionsList
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.category.CategoryIdToCategory
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
        categoryIdToCategory: CategoryIdToCategory
    ): CategoryToTransactionsList

    /**
     * @return map of transactions associated with their categories
     * ([CategoryWithParent]] also includes category's parent).
     */
    fun linkTransactionsWithCategories(
        transactions: List<Transaction>,
        categoryIdToCategory: CategoryIdToCategory
    ): TransactionToCategory

}