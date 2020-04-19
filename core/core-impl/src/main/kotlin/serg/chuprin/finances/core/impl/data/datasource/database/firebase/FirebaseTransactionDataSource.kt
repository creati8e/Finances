package serg.chuprin.finances.core.impl.data.datasource.database.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.api.extensions.toDateUTC
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_AMOUNT
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_DATE
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_MONEY_ACCOUNT_ID
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_TYPE
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class FirebaseTransactionDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun userTransactionsFlow(userId: Id): Flow<List<DocumentSnapshot>> {
        return callbackFlow {
            firestore
                .collection(COLLECTION_NAME)
                .whereEqualTo(FIELD_OWNER_ID, userId.value)
                .suspending(
                    this@callbackFlow,
                    mapper = QuerySnapshot::getDocuments
                )
        }
    }

    fun createTransaction(transaction: Transaction) {
        firestore
            .collection(COLLECTION_NAME)
            .document(transaction.id.value)
            .set(transaction.toMap())
    }

    private fun Transaction.toMap(): Map<String, Any> {
        return mapOf(
            FIELD_TYPE to type,
            FIELD_OWNER_ID to ownerId.value,
            FIELD_DATE to dateTime.toDateUTC(),
            FIELD_AMOUNT to amount.toPlainString(),
            FIELD_MONEY_ACCOUNT_ID to moneyAccountId.value
        )
    }

}