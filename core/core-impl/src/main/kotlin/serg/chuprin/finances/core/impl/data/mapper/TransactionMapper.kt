package serg.chuprin.finances.core.impl.data.mapper

import android.annotation.SuppressLint
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.api.domain.model.TransactionType
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_AMOUNT
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_CURRENCY_CODE
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_DATE
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_MONEY_ACCOUNT_ID
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_TYPE
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class TransactionMapper @Inject constructor() :
    ModelMapper<DocumentSnapshot, Transaction> {

    override fun invoke(snapshot: DocumentSnapshot): Transaction? {
        val transactionType = snapshot.getString(FIELD_TYPE)?.let { type ->
            @SuppressLint("DefaultLocale")
            when (type.toLowerCase()) {
                FirebaseTransactionFieldsContract.Type.PLAIN -> TransactionType.PLAIN
                FirebaseTransactionFieldsContract.Type.BALANCE -> TransactionType.BALANCE
                else -> null
            }
        }
        return try {
            Transaction.create(
                id = snapshot.id,
                type = transactionType,
                date = snapshot.getDate(FIELD_DATE),
                amount = snapshot.getString(FIELD_AMOUNT),
                ownerId = snapshot.getString(FIELD_OWNER_ID),
                currencyCode = snapshot.getString(FIELD_CURRENCY_CODE),
                moneyAccountId = snapshot.getString(FIELD_MONEY_ACCOUNT_ID)
            )
        } catch (throwable: Throwable) {
            Timber.d(throwable) { "An error occurred when mapping transaction" }
            null
        }

    }

}