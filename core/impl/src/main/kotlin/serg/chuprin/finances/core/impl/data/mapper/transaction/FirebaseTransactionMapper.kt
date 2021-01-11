package serg.chuprin.finances.core.impl.data.mapper.transaction

import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.extensions.nonNullValuesMap
import serg.chuprin.finances.core.api.extensions.toDateUTC
import serg.chuprin.finances.core.api.extensions.toLocalDateTimeUTC
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_AMOUNT
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_CATEGORY_ID
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_CURRENCY_CODE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_DATE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_MONEY_ACCOUNT_ID
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_TYPE
import serg.chuprin.finances.core.impl.data.mapper.base.FirebaseModelMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class FirebaseTransactionMapper @Inject constructor(
    private val typeMapper: FirebaseTransactionTypeMapper
) : FirebaseModelMapper<Transaction> {

    override fun mapFromSnapshot(snapshot: DocumentSnapshot): Transaction? {
        return Transaction.create(
            id = snapshot.id,
            amount = snapshot.getString(FIELD_AMOUNT),
            ownerId = snapshot.getString(FIELD_OWNER_ID),
            categoryId = snapshot.getString(FIELD_CATEGORY_ID),
            currencyCode = snapshot.getString(FIELD_CURRENCY_CODE),
            moneyAccountId = snapshot.getString(FIELD_MONEY_ACCOUNT_ID),
            dateTime = snapshot.getDate(FIELD_DATE)?.toLocalDateTimeUTC(),
            type = snapshot.getString(FIELD_TYPE)?.let(typeMapper::mapTo)
        )
    }

    override fun mapToFieldsMap(model: Transaction): Map<String, Any> {
        return nonNullValuesMap(
            FIELD_OWNER_ID to model.ownerId.value,
            FIELD_DATE to model.dateTime.toDateUTC(),
            FIELD_CURRENCY_CODE to model.currencyCode,
            FIELD_AMOUNT to model.amount.toPlainString(),
            FIELD_CATEGORY_ID to model.categoryId?.value,
            FIELD_TYPE to typeMapper.mapFrom(model.type),
            FIELD_MONEY_ACCOUNT_ID to model.moneyAccountId.value
        )
    }

}