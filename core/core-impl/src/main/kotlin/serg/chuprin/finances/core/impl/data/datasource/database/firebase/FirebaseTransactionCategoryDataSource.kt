package serg.chuprin.finances.core.impl.data.datasource.database.firebase

import com.google.firebase.firestore.FirebaseFirestore
import serg.chuprin.finances.core.api.domain.model.TransactionCategory
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_NAME
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_PARENT_ID
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
class FirebaseTransactionCategoryDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun createTransactions(transactionCategories: List<TransactionCategory>) {
        val collection = firestore.collection(COLLECTION_NAME)
        firestore.runBatch { writeBatch ->
            transactionCategories.forEach { transactionCategory ->
                writeBatch.set(
                    collection.document(transactionCategory.id.value),
                    transactionCategory.toMap()
                )
                writeBatch.commit()
            }
        }
    }

    private fun TransactionCategory.toMap(): Map<String, Any?> {
        return mapOf(
            FIELD_NAME to name,
            FIELD_PARENT_ID to parentCategoryId?.value
        )
    }

}