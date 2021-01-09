package serg.chuprin.finances.core.impl.domain.linker

import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.TransactionToCategory
import serg.chuprin.finances.core.api.domain.model.CategoryToTransactionsList
import serg.chuprin.finances.core.api.domain.model.category.CategoryIdToCategory
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.05.2020.
 */
internal class TransactionWithCategoriesLinkerImpl
@Inject constructor() : TransactionWithCategoriesLinker {

    override fun linkCategoryParentsWithTransactions(
        transactions: List<Transaction>,
        categoryIdToCategory: CategoryIdToCategory
    ): CategoryToTransactionsList {
        return CategoryToTransactionsList(
            transactions.groupBy { transaction ->
                categoryIdToCategory[transaction.categoryId]?.run {
                    parentCategory ?: category
                }
            }
        )
    }

    override fun linkTransactionsWithCategories(
        transactions: List<Transaction>,
        categoryIdToCategory: CategoryIdToCategory
    ): TransactionToCategory {
        return TransactionToCategory(
            transactions.associateBy(
                { transaction -> transaction },
                { transaction -> categoryIdToCategory[transaction.categoryId] }
            )
        )
    }

}