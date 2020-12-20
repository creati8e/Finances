package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
data class TransactionReportRawData(
    val chartData: Map<TransactionCategory?, List<Transaction>>,
    val listData: Map<Transaction, TransactionCategoryWithParent?>
)