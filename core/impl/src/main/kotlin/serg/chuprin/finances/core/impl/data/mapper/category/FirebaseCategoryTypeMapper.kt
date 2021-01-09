package serg.chuprin.finances.core.impl.data.mapper.category

import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseCategoryFieldsContract.TYPE_EXPENSE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseCategoryFieldsContract.TYPE_INCOME
import serg.chuprin.finances.core.impl.data.mapper.base.ModelMapper
import serg.chuprin.finances.core.impl.data.mapper.base.ReverseModelMapper
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
internal class FirebaseCategoryTypeMapper @Inject constructor() :
    ModelMapper<String, CategoryType>,
    ReverseModelMapper<String, CategoryType> {

    override fun mapTo(model: String): CategoryType? {
        return when (model.toLowerCase(Locale.ROOT)) {
            TYPE_INCOME -> CategoryType.INCOME
            TYPE_EXPENSE -> CategoryType.EXPENSE
            else -> null
        }
    }

    override fun mapFrom(model: CategoryType): String {
        return when (model) {
            CategoryType.INCOME -> TYPE_INCOME
            CategoryType.EXPENSE -> TYPE_EXPENSE
        }
    }

}