package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionCategory
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.mapper.category.FirebaseTransactionCategoryMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class FirebaseTransactionCategoryDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val mapper: FirebaseTransactionCategoryMapper
) {

    fun createTransactions(transactionCategories: List<TransactionCategory>) {
        val collection = getCollection()
        firestore.runBatch { writeBatch ->
            transactionCategories.forEach { transactionCategory ->
                writeBatch.set(
                    collection.document(transactionCategory.id.value),
                    mapper.mapToFieldsMap(transactionCategory)
                )
            }
        }
    }

    fun categoriesFlow(categoryIds: List<Id>): Flow<List<DocumentSnapshot>> {
        if (categoryIds.isEmpty()) {
            return flowOf(emptyList())
        }
        return callbackFlow {
            getCollection()
                .whereIn(FieldPath.documentId(), categoryIds.map(Id::value))
                .suspending(this) { querySnapshot -> querySnapshot.documents }
        }
    }

    private fun getCollection() = firestore.collection(COLLECTION_NAME)

}