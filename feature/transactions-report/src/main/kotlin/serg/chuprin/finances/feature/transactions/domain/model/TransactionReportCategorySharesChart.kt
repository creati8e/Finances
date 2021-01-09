package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.CategoryShares
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 24.12.2020.
 */
data class TransactionReportCategorySharesChart(
    val currency: Currency,
    val totalAmount: BigDecimal,
    /**
     * List of category amount pairs sorted by amount descending.
     */
    val categoryShares: CategoryShares,
    val transactionType: PlainTransactionType
)