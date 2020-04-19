package serg.chuprin.finances.core.impl.data.mapper

import serg.chuprin.finances.core.api.domain.model.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.TransactionCategoryType
import serg.chuprin.finances.core.impl.data.datasource.assets.TransactionCategoryAssetDto
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
class PredefinedTransactionCategoryMapper @Inject constructor() :
    ModelMapper<TransactionCategoryAssetDto, TransactionCategory> {

    override fun invoke(model: TransactionCategoryAssetDto): TransactionCategory? {
        val type = if (model.isIncome) {
            TransactionCategoryType.INCOME
        } else {
            TransactionCategoryType.EXPENSE
        }
        return TransactionCategory.create(
            type = type,
            id = model.id,
            name = model.name,
            parentCategoryId = model.parentCategoryId
        )
    }

}