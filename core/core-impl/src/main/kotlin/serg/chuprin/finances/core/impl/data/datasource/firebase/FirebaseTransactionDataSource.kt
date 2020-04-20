package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.mapper.transaction.FirebaseTransactionMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class FirebaseTransactionDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val transactionMapper: FirebaseTransactionMapper
) {

    fun userTransactionsFlow(userId: Id): Flow<List<DocumentSnapshot>> {
        return callbackFlow {
            getUserTransactionsCollection(userId)
                .suspending(
                    this@callbackFlow,
                    mapper = QuerySnapshot::getDocuments
                )
        }
    }

    fun recentUserTransactionsFlow(userId: Id, count: Int): Flow<List<DocumentSnapshot>> {
        return callbackFlow {
            getUserTransactionsCollection(userId)
                .orderBy(FirebaseTransactionFieldsContract.FIELD_DATE, Query.Direction.DESCENDING)
                .limit(count.toLong())
                .suspending(
                    this@callbackFlow,
                    mapper = QuerySnapshot::getDocuments
                )
        }
    }

    fun createTransaction(transaction: Transaction) {
        getCollection()
            .document(transaction.id.value)
            .set(transactionMapper.mapToFieldsMap(transaction))
    }

    private fun getUserTransactionsCollection(userId: Id): Query {
        return getCollection().whereEqualTo(FIELD_OWNER_ID, userId.value)
    }

    private fun getCollection(): CollectionReference = firestore.collection(COLLECTION_NAME)

}