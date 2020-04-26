package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_PARENT_ID
import serg.chuprin.finances.core.impl.data.mapper.category.FirebaseTransactionCategoryMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class FirebaseTransactionCategoryDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val mapper: FirebaseTransactionCategoryMapper
) {

    fun createCategories(transactionCategories: List<TransactionCategory>) {
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

    @Suppress("MoveLambdaOutsideParentheses")
    fun categoriesWithParentsFlow(categoryIds: List<String>): Flow<List<DocumentSnapshot>> {
        if (categoryIds.isEmpty()) {
            return flowOf(emptyList())
        }
        return categoriesFlow(categoryIds)
            .flatMapLatest { documents ->
                val parentCategoryDocumentsIds = documents
                    .mapNotNull { document ->
                        document
                            .getString(FIELD_PARENT_ID)
                            ?.takeUnless(String::isEmpty)
                    }
                    .distinct()
                combine(
                    flowOf(documents),
                    categoriesFlow(parentCategoryDocumentsIds),
                    { categoryDocuments, parentCategoryDocuments ->
                        categoryDocuments + parentCategoryDocuments
                    }
                )
            }
    }

    private fun categoriesFlow(categoryIds: List<String>): Flow<List<DocumentSnapshot>> {
        if (categoryIds.isEmpty()) {
            return flowOf(emptyList())
        }
        return getCollection()
            .whereIn(FieldPath.documentId(), categoryIds)
            .asFlow()
            .map { querySnapshot -> querySnapshot.documents }
    }

    private fun getCollection() = firestore.collection(COLLECTION_NAME)

}