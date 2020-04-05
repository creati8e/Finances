package serg.chuprin.finances.core.impl.data.database.firebase.datasource

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseTransactionFieldsContract.FIELD_USER_ID
import serg.chuprin.finances.core.impl.data.database.firebase.suspending
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class FirebaseTransactionDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private companion object {
        private const val COLLECTION = "transaction"
    }

    init {
        // TODO: Move setup to other place.
        val settings = FirebaseFirestoreSettings
            .Builder()
            .setPersistenceEnabled(true)
            .build()
        firestore.firestoreSettings = settings
    }

    fun userTransactionsFlow(userId: Id): Flow<List<DocumentSnapshot>> {
        return callbackFlow {
            firestore
                .collection(COLLECTION)
                .whereEqualTo(FIELD_USER_ID, userId.value)
                .suspending(
                    this@callbackFlow,
                    mapper = QuerySnapshot::getDocuments
                )
        }
    }

}