package serg.chuprin.finances.core.api.domain.service

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.TransactionCategoriesMap
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery

/**
 * Created by Sergey Chuprin on 20.04.2020.
 *
 * This class retrieves transactions and categories.
 * It combines them in different ways depending of required operation.
 */
interface TransactionCategoryRetrieverService {

    /**
     * @return flow emitting map of transactions associated with their categories
     * ([TransactionCategoryWithParent]] also includes category's parent).
     */
    fun transactionsFlow(query: TransactionsQuery): Flow<TransactionCategoriesMap>

    /**
     * @return flow emitting map of parent categories associated with transactions.
     * Transactions with child categories are associated with their parents.
     */
    fun categoryTransactionsFlow(
        query: TransactionsQuery
    ): Flow<Map<TransactionCategory?, List<Transaction>>>

}