package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_PARENT_ID
import serg.chuprin.finances.core.impl.data.mapper.category.FirebaseTransactionCategoryMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class FirebaseTransactionCategoryDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val mapper: FirebaseTransactionCategoryMapper,
    private val queryExecutor: FirebaseTransactionCategoryQueryExecutor
) {

    suspend fun getAllUserCategories(userId: Id): List<DocumentSnapshot> {
        return getCollection()
            .whereEqualTo(FIELD_OWNER_ID, userId.value)
            .get()
            .await()
            ?.documents
            .orEmpty()
    }

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

    suspend fun deleteCategories(categories: List<TransactionCategory>) {
        val collection = getCollection()
        firestore
            .runBatch { writeBatch ->
                categories.forEach { transaction ->
                    writeBatch.delete(collection.document(transaction.id.value))
                }
            }
            .awaitWithLogging {
                "Categories were deleted"
            }
    }

    fun categoriesFlow(query: TransactionCategoriesQuery): Flow<List<DocumentSnapshot>> {
        return queryExecutor.execute(query)
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
            .asFlow()
            .map { querySnapshot ->
                querySnapshot.documents.filter { document ->
                    document.id in categoryIds
                }
            }
    }

    private fun getCollection() = firestore.collection(COLLECTION_NAME)

}