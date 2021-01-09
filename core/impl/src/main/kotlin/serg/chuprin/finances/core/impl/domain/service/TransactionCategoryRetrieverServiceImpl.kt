package serg.chuprin.finances.core.impl.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionToCategory
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.extensions.categoryIds
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
internal class TransactionCategoryRetrieverServiceImpl @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionWithCategoriesLinker: TransactionWithCategoriesLinker
) : TransactionCategoryRetrieverService {

    override fun transactionsFlow(
        ownerId: Id,
        query: TransactionsQuery
    ): Flow<TransactionToCategory> {
        return transactionRepository
            .transactionsFlow(query)
            .flatMapLatest { transactions ->
                combine(
                    flowOf(transactions),
                    categoryRepository.categoriesFlow(
                        CategoriesQuery(
                            ownerId = ownerId,
                            categoryIds = transactions.categoryIds,
                            relation = CategoriesQuery.Relation.RETRIEVE_PARENTS
                        )
                    ),
                    transactionWithCategoriesLinker::linkTransactionsWithCategories
                )
            }
    }

}