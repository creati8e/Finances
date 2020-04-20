package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.FirebaseFirestore
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
        val collection = firestore.collection(COLLECTION_NAME)
        firestore.runBatch { writeBatch ->
            transactionCategories.forEach { transactionCategory ->
                writeBatch.set(
                    collection.document(transactionCategory.id.value),
                    mapper.mapToFieldsMap(transactionCategory)
                )
            }
        }
    }

}