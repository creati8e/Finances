package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.TransactionToCategory
import serg.chuprin.finances.core.api.domain.model.CategoryToTransactionsList

/**
 * Created by Sergey Chuprin on 25.12.2020.
 */
data class TransactionReportCurrentPeriodData(
    val transactionToCategory: TransactionToCategory,
    val categoryToTransactionsList: CategoryToTransactionsList
)