package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery
import serg.chuprin.finances.core.api.extensions.toDateUTC
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_DATE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.mapper.transaction.FirebaseTransactionMapper
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class FirebaseTransactionDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val transactionMapper: FirebaseTransactionMapper
) {

    suspend fun deleteTransactions(transactions: List<Transaction>) {
        val collection = getCollection()
        firestore
            .runBatch { writeBatch ->
                transactions.forEach { transaction ->
                    writeBatch.delete(collection.document(transaction.id.value))
                }
            }
            .awaitWithLogging {
                "Transactions were deleted"
            }
    }

    fun createTransaction(transaction: Transaction) {
        getCollection()
            .document(transaction.id.value)
            .set(transactionMapper.mapToFieldsMap(transaction))
    }

    fun transactionsFlow(query: TransactionsQuery): Flow<List<DocumentSnapshot>> {
        return getCollection()
            .limit(query.limit)
            .sortBy(query.sortOrder)
            .filterByUser(query.userId)
            .filterByDate(query.startDate, query.endDate)
            .asFlow()
            .map { querySnapshot -> querySnapshot.documents }
    }

    private fun Query.filterByUser(userId: Id? = null): Query {
        return if (userId != null) whereEqualTo(FIELD_OWNER_ID, userId.value) else this
    }

    private fun Query.limit(limit: Int?): Query {
        return if (limit != null) limit(limit.toLong()) else this
    }

    private fun Query.sortBy(sortOrder: TransactionsQuery.SortOrder?): Query {
        return when (sortOrder) {
            TransactionsQuery.SortOrder.DATE_DESC -> {
                orderBy(FIELD_DATE, Query.Direction.DESCENDING)
            }
            TransactionsQuery.SortOrder.DATE_ASC -> {
                orderBy(FIELD_DATE, Query.Direction.ASCENDING)
            }
            null -> this
        }
    }

    private fun Query.filterByDate(
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): Query {
        if (startDate == null && endDate == null) {
            return this
        }
        return whereGreaterThanOrEqualTo(FIELD_DATE, startDate!!.toDateUTC())
            .whereLessThanOrEqualTo(FIELD_DATE, endDate!!.toDateUTC())
    }

    private fun getCollection(): CollectionReference = firestore.collection(COLLECTION_NAME)

}