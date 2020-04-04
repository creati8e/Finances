package serg.chuprin.finances.core.impl.data.database.firebase.datasource

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import serg.chuprin.finances.core.api.domain.model.Id
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
class FirebaseTransactionDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private companion object {
        private const val DOCUMENT = "transaction"
    }

    init {
        val settings = FirebaseFirestoreSettings
            .Builder()
            .setPersistenceEnabled(true)
            .build()
        firestore.firestoreSettings = settings
    }

    fun userTransactionsFlow(userId: Id): Flow<List<DocumentSnapshot>> {
        return callbackFlow {
            val document = firestore.collection(DOCUMENT).whereEqualTo("id", userId.value)
            val snapshotListener = document.addSnapshotListener { querySnapshot, _ ->
                querySnapshot?.documents?.let { offer(it) }
            }
            invokeOnClose { snapshotListener.remove() }
        }
    }

}