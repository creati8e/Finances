package serg.chuprin.finances.core.api.domain.model.category

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
data class TransactionCategoryWithChildren(
    val category: TransactionCategory,
    val children: List<TransactionCategory>
)