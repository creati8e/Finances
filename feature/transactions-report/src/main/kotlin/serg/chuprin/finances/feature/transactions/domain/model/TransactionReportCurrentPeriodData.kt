package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 25.12.2020.
 */
data class TransactionReportCurrentPeriodData(
    val transactions: Map<Transaction, TransactionCategoryWithParent?>,
    val categoryTransactions: Map<TransactionCategory?, List<Transaction>>
)