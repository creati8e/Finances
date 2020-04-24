package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.api.extensions.toDateUTC
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_DATE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionFieldsContract.FIELD_MONEY_ACCOUNT_ID
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

    fun userTransactionsFlow(
        userId: Id,
        dataPeriod: DataPeriod?
    ): Flow<List<DocumentSnapshot>> {
        return getUserTransactionsCollection(userId)
            .filterByDataPeriod(dataPeriod)
            .asFlow()
            .map { querySnapshot -> querySnapshot.documents }
    }

    fun moneyAccountTransactionsFlow(moneyAccountId: Id): Flow<List<DocumentSnapshot>> {
        return getCollection()
            .whereEqualTo(FIELD_MONEY_ACCOUNT_ID, moneyAccountId.value)
            .asFlow()
            .map { querySnapshot -> querySnapshot.documents }
    }

    fun recentUserTransactionsFlow(
        userId: Id,
        count: Int,
        dataPeriod: DataPeriod
    ): Flow<List<DocumentSnapshot>> {
        return getUserTransactionsCollection(userId)
            .filterByDataPeriod(dataPeriod)
            .orderBy(FIELD_DATE, Query.Direction.DESCENDING)
            .limit(count.toLong())
            .asFlow()
            .map { querySnapshot -> querySnapshot.documents }
    }

    fun createTransaction(transaction: Transaction) {
        getCollection()
            .document(transaction.id.value)
            .set(transactionMapper.mapToFieldsMap(transaction))
    }

    private fun Query.filterByDataPeriod(dataPeriod: DataPeriod?): Query {
        if (dataPeriod == null) {
            return this
        }
        return whereGreaterThanOrEqualTo(FIELD_DATE, dataPeriod.startDate.toDateUTC())
            .whereLessThanOrEqualTo(FIELD_DATE, dataPeriod.endDate.toDateUTC())
    }

    private fun getUserTransactionsCollection(userId: Id): Query {
        return getCollection().whereEqualTo(FIELD_OWNER_ID, userId.value)
    }

    private fun getCollection(): CollectionReference = firestore.collection(COLLECTION_NAME)

}