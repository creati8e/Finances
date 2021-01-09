package serg.chuprin.finances.core.api.domain.linker

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionCategories
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
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
        categoryWithParentMap: Map<Id, CategoryWithParent>
    ): Map<Category?, List<Transaction>>

    /**
     * @return map of transactions associated with their categories
     * ([CategoryWithParent]] also includes category's parent).
     */
    fun linkTransactionsWithCategories(
        transactions: List<Transaction>,
        categoryWithParentMap: Map<Id, CategoryWithParent>
    ): TransactionCategories

}