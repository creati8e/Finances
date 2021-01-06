package serg.chuprin.finances.core.impl.data.mapper

import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseDataPeriodTypeFieldsContract.TYPE_DAY
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseDataPeriodTypeFieldsContract.TYPE_MONTH
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseDataPeriodTypeFieldsContract.TYPE_WEEK
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseDataPeriodTypeFieldsContract.TYPE_YEAR
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
            TYPE_DAY -> DataPeriodType.DAY
            TYPE_WEEK -> DataPeriodType.WEEK
            TYPE_YEAR -> DataPeriodType.YEAR
            else -> null
        }
    }

    override fun mapFrom(model: DataPeriodType): String {
        return when (model) {
            DataPeriodType.DAY -> TYPE_DAY
            DataPeriodType.WEEK -> TYPE_WEEK
            DataPeriodType.YEAR -> TYPE_YEAR
            DataPeriodType.MONTH -> TYPE_MONTH
        }
    }

}