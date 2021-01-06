package serg.chuprin.finances.core.impl.domain.linker

import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.05.2020.
 */
internal class TransactionWithCategoriesLinkerImpl
@Inject constructor() : TransactionWithCategoriesLinker {

    override fun linkCategoryParentsWithTransactions(
        transactions: List<Transaction>,
        categoryWithParentMap: Map<Id, TransactionCategoryWithParent>
    ): Map<TransactionCategory?, List<Transaction>> {
        return transactions.groupBy { transaction ->
            categoryWithParentMap[transaction.categoryId]?.run {
                parentCategory ?: category
            }
        }
    }


    override fun linkTransactionsWithCategories(
        transactions: List<Transaction>,
        categoryWithParentMap: Map<Id, TransactionCategoryWithParent>
    ): Map<Transaction, TransactionCategoryWithParent?> {
        return transactions.associateBy(
            { transaction -> transaction },
            { transaction -> categoryWithParentMap[transaction.categoryId] }
        )
    }

}