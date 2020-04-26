package serg.chuprin.finances.core.api.domain.model.category

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
data class TransactionCategoryWithParent(
    val category: TransactionCategory,
    val parentCategory: TransactionCategory?
)