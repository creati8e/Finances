package serg.chuprin.finances.core.impl.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.TransactionCategoriesMap
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.extensions.categoryIds
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
internal class TransactionCategoryRetrieverServiceImpl @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: TransactionCategoryRepository,
    private val transactionWithCategoriesLinker: TransactionWithCategoriesLinker
) : TransactionCategoryRetrieverService {

    override fun transactionsFlow(query: TransactionsQuery): Flow<TransactionCategoriesMap> {
        return transactionRepository
            .transactionsFlow(query)
            .flatMapLatest { transactions ->
                combine(
                    flowOf(transactions),
                    categoryRepository.categoriesFlow(
                        TransactionCategoriesQuery(
                            categoryIds = transactions.categoryIds.toSet(),
                            relation = TransactionCategoriesQuery.Relation.RETRIEVE_PARENTS
                        )
                    ),
                    transactionWithCategoriesLinker::linkTransactionsWithCategories
                )
            }
    }

    override fun categoryTransactionsFlow(
        query: TransactionsQuery
    ): Flow<Map<TransactionCategory?, List<Transaction>>> {
        return transactionRepository
            .transactionsFlow(query)
            .flatMapLatest { transactions ->
                combine(
                    flowOf(transactions),
                    categoryRepository.categoriesFlow(
                        TransactionCategoriesQuery(
                            categoryIds = transactions.categoryIds.toSet(),
                            relation = TransactionCategoriesQuery.Relation.RETRIEVE_PARENTS
                        )
                    ),
                    transactionWithCategoriesLinker::linkCategoryParentsWithTransactions
                )
            }
    }

}