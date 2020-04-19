package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
class PredefinedTransactionCategories(
    val incomeCategories: List<TransactionCategory>,
    val expenseCategories: List<TransactionCategory>
) 