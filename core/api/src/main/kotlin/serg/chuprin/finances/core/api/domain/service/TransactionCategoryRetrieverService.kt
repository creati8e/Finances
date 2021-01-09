package serg.chuprin.finances.core.api.domain.service

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionToCategory
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery

/**
 * Created by Sergey Chuprin on 20.04.2020.
 *
 * This class retrieves transactions and categories.
 * It combines them in different ways depending of required operation.
 */
interface TransactionCategoryRetrieverService {

    /**
     * @return flow emitting map of transactions associated with their categories
     * ([CategoryWithParent]] also includes category's parent).
     */
    fun transactionsFlow(
        ownerId: Id,
        query: TransactionsQuery
    ): Flow<TransactionToCategory>

}