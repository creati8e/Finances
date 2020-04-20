package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
data class TransactionWithCategory(
    val transaction: Transaction,
    val category: TransactionCategory
)