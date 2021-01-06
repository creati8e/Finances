package serg.chuprin.finances.core.impl.data.model

import serg.chuprin.finances.core.impl.data.datasource.assets.TransactionCategoryAssetDto

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class PredefinedTransactionCategories(
    val incomeCategories: List<TransactionCategoryAssetDto>,
    val expenseCategories: List<TransactionCategoryAssetDto>
)