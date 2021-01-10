package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.extensions.contains
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
    firestore: FirebaseFirestore,
    private val mapper: FirebaseTransactionMapper
) : BaseFirebaseDataSource(firestore) {

    override val collection: CollectionReference
        get() = firestore.collection(COLLECTION_NAME)

    fun deleteTransactions(transactionIds: Collection<Id>) = delete(transactionIds)

    fun createOrUpdate(transactions: Collection<Transaction>) {
        createOrUpdate(transactions.associateBy(Transaction::id, mapper::mapToFieldsMap))
    }

    fun transactionsFlow(query: TransactionsQuery): Flow<List<DocumentSnapshot>> {
        return collection
            .limit(query.limit)
            .sortBy(query.sortOrder)
            .filterByOwner(query.ownerId)
            .filterByDate(query.startDate, query.endDate)
            .asFlow()
            .map { querySnapshot ->
                querySnapshot.documents.filterByTransactionIds(query.transactionIds)
            }
    }

    private fun List<DocumentSnapshot>.filterByTransactionIds(
        transactionIds: Set<Id>
    ): List<DocumentSnapshot> {
        if (transactionIds.isEmpty()) {
            return this
        }
        return filter { documentSnapshot ->
            transactionIds.contains { id ->
                id.value == documentSnapshot.id
            }
        }
    }

    private fun Query.filterByOwner(ownerId: Id? = null): Query {
        return if (ownerId != null) whereEqualTo(FIELD_OWNER_ID, ownerId.value) else this
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

}