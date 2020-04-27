package serg.chuprin.finances.core.impl.data.mapper.category

import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory.Companion.create
import serg.chuprin.finances.core.api.extensions.nonNullValuesMap
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_COLOR_HEX
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_PARENT_ID
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_TYPE
import serg.chuprin.finances.core.impl.data.mapper.base.FirebaseModelMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class FirebaseTransactionCategoryMapper @Inject constructor(
    private val typeMapper: FirebaseTransactionCategoryTypeMapper
) : FirebaseModelMapper<TransactionCategory> {

    override fun mapFromSnapshot(snapshot: DocumentSnapshot): TransactionCategory? {
        return create(
            id = snapshot.id,
            name = snapshot.getString(FIELD_NAME),
            ownerId = snapshot.getString(FIELD_OWNER_ID),
            colorHex = snapshot.getString(FIELD_COLOR_HEX),
            parentCategoryId = snapshot.getString(FIELD_PARENT_ID),
            type = snapshot.getString(FIELD_TYPE)?.let(typeMapper::mapTo)
        )
    }

    override fun mapToFieldsMap(model: TransactionCategory): Map<String, Any> {
        return nonNullValuesMap(
            FIELD_NAME to model.name,
            FIELD_TYPE to model.type,
            FIELD_COLOR_HEX to model.colorHex,
            FIELD_OWNER_ID to model.ownerId.value,
            FIELD_TYPE to typeMapper.mapFrom(model.type),
            FIELD_PARENT_ID to model.parentCategoryId?.value
        )
    }

}