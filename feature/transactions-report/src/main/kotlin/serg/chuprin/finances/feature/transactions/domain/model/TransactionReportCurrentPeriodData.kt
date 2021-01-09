package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.TransactionCategories
import serg.chuprin.finances.core.api.domain.model.CategoryToTransactionsList

/**
 * Created by Sergey Chuprin on 25.12.2020.
 */
data class TransactionReportCurrentPeriodData(
    val transactionCategories: TransactionCategories,
    val categoryToTransactionsList: CategoryToTransactionsList
)