package serg.chuprin.finances.core.impl.data.mapper

import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.TransactionCategory.Companion.create
import serg.chuprin.finances.core.api.domain.model.TransactionCategoryType
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_NAME
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_PARENT_ID
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_TYPE
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.TYPE_EXPENSE
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.TYPE_INCOME
import java.util.*

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
class TransactionCategoryMapper : ModelMapper<DocumentSnapshot, TransactionCategory> {

    override fun invoke(model: DocumentSnapshot): TransactionCategory? {
        val type = when (model.getString(FIELD_TYPE)?.toLowerCase(Locale.ROOT)) {
            TYPE_EXPENSE -> TransactionCategoryType.EXPENSE
            TYPE_INCOME -> TransactionCategoryType.INCOME
            else -> null
        }
        return create(
            type = type,
            id = model.id,
            name = model.getString(FIELD_NAME),
            ownerId = model.getString(FIELD_OWNER_ID),
            parentCategoryId = model.getString(FIELD_PARENT_ID)
        )
    }

}