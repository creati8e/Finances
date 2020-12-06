package serg.chuprin.finances.core.impl.data.mapper.transaction

import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.Type.BALANCE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.Type.PLAIN
import serg.chuprin.finances.core.impl.data.mapper.base.ModelMapper
import serg.chuprin.finances.core.impl.data.mapper.base.ReverseModelMapper
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
internal class FirebaseTransactionTypeMapper @Inject constructor() :
    ModelMapper<String, TransactionType>,
    ReverseModelMapper<String, TransactionType> {

    override fun mapTo(model: String): TransactionType? {
        return when (model.toLowerCase(Locale.ROOT)) {
            PLAIN -> TransactionType.PLAIN
            BALANCE -> TransactionType.BALANCE
            else -> null
        }
    }

    override fun mapFrom(model: TransactionType): String {
        return when (model) {
            TransactionType.PLAIN -> PLAIN
            TransactionType.BALANCE -> BALANCE
        }
    }

}