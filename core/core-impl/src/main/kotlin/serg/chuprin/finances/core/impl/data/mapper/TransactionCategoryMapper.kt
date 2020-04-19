package serg.chuprin.finances.core.impl.data.mapper

import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.TransactionCategory.Companion.create
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_NAME
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_PARENT_ID

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
class TransactionCategoryMapper : ModelMapper<DocumentSnapshot, TransactionCategory> {

    override fun invoke(model: DocumentSnapshot): TransactionCategory? {
        return create(
            model.id,
            model.getString(FIELD_NAME),
            model.getString(FIELD_PARENT_ID)
        )
    }

}