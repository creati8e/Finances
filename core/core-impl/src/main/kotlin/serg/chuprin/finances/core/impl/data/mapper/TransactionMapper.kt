package serg.chuprin.finances.core.impl.data.mapper

import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_AMOUNT
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_DATE
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_USER_ID
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class TransactionMapper @Inject constructor() :
    ModelMapper<DocumentSnapshot, Transaction> {

    override fun invoke(snapshot: DocumentSnapshot): Transaction? {
        return try {
            Transaction.create(
                id = snapshot.id,
                date = snapshot.getDate(FIELD_DATE),
                amount = snapshot.getString(FIELD_AMOUNT),
                userId = snapshot.getString(FIELD_USER_ID)
            )
        } catch (throwable: Throwable) {
            Timber.d(throwable) { "An error occurred when mapping transaction" }
            null
        }

    }

}