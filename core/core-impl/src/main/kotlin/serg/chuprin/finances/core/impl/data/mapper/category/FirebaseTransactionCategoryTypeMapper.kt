package serg.chuprin.finances.core.impl.data.mapper.category

import serg.chuprin.finances.core.api.domain.model.TransactionCategoryType
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.TYPE_EXPENSE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.TYPE_INCOME
import serg.chuprin.finances.core.impl.data.mapper.base.ModelMapper
import serg.chuprin.finances.core.impl.data.mapper.base.ReverseModelMapper
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class FirebaseTransactionCategoryTypeMapper @Inject constructor() :
    ModelMapper<String, TransactionCategoryType>,
    ReverseModelMapper<String, TransactionCategoryType> {

    override fun mapTo(model: String): TransactionCategoryType? {
        return when (model.toLowerCase(Locale.ROOT)) {
            TYPE_INCOME -> TransactionCategoryType.INCOME
            TYPE_EXPENSE -> TransactionCategoryType.EXPENSE
            else -> null
        }
    }

    override fun mapFrom(model: TransactionCategoryType): String? {
        return when (model) {
            TransactionCategoryType.INCOME -> TYPE_INCOME
            TransactionCategoryType.EXPENSE -> TYPE_EXPENSE
        }
    }

}