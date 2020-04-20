package serg.chuprin.finances.core.impl.data.mapper

import serg.chuprin.finances.core.api.domain.model.DataPeriodType
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseDataPeriodTypeFieldsContract.TYPE_MONTH
import serg.chuprin.finances.core.impl.data.mapper.base.ModelMapper
import serg.chuprin.finances.core.impl.data.mapper.base.ReverseModelMapper
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
internal class FirebaseDataPeriodTypeMapper @Inject constructor() :
    ModelMapper<String, DataPeriodType>,
    ReverseModelMapper<String, DataPeriodType> {

    override fun mapTo(model: String): DataPeriodType? {
        return when (model.toLowerCase(Locale.ROOT)) {
            TYPE_MONTH -> DataPeriodType.MONTH
            else -> null
        }
    }

    override fun mapFrom(model: DataPeriodType): String? {
        return when (model) {
            DataPeriodType.MONTH -> TYPE_MONTH
        }
    }

}