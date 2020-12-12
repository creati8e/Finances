package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.query.TransactionCategoriesQuery.Relation
import serg.chuprin.finances.core.api.extensions.contains
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_PARENT_ID
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseTransactionCategoryFieldsContract.FIELD_TYPE
import serg.chuprin.finances.core.impl.data.mapper.category.FirebaseTransactionCategoryTypeMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
internal class FirebaseTransactionCategoryQueryExecutor @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val typeMapper: FirebaseTransactionCategoryTypeMapper
) {

    fun execute(query: TransactionCategoriesQuery): Flow<List<DocumentSnapshot>> {
        return when (query.relation) {
            Relation.RETRIEVE_CHILDREN -> {
                mergeParentAndChildrenCategories(query)
            }
            null -> {
                buildQueryFlow(query).map { querySnapshot ->
                    if (query.categoryIds.isEmpty()) {
                        querySnapshot.documents
                    } else {
                        querySnapshot.documents.filter { document ->
                            query.categoryIds.contains { id -> id.value == document.id }
                        }
                    }
                }
            }
        }
    }

    private fun mergeParentAndChildrenCategories(
        query: TransactionCategoriesQuery
    ): Flow<List<DocumentSnapshot>> {
        return flow {
            coroutineScope {
                val sharedFlow = buildQueryFlow(query).shareIn(
                    scope = this,
                    started = SharingStarted.WhileSubscribed(replayExpirationMillis = 0),
                )

                val parentCategoriesFlow = sharedFlow.map { querySnapshot ->
                    if (query.categoryIds.isEmpty()) {
                        querySnapshot.documents
                    } else {
                        querySnapshot.documents.filter { document ->
                            query.categoryIds.contains { id -> id.value == document.id }
                        }
                    }
                }

                val childrenCategoriesFlow = sharedFlow.map { querySnapshot ->
                    if (query.categoryIds.isEmpty()) {
                        querySnapshot.documents
                    } else {
                        querySnapshot.documents.filter { document ->
                            query.categoryIds.contains { id ->
                                id.value == document.getString(FIELD_PARENT_ID)
                            }
                        }
                    }
                }
                emitAll(combine(parentCategoriesFlow, childrenCategoriesFlow, ::mergeDocuments))
            }
        }
    }

    private fun buildQueryFlow(query: TransactionCategoriesQuery): Flow<QuerySnapshot> {
        return firestore
            .collection(COLLECTION_NAME)
            .filterByUser(query.userId)
            .filterByType(query.type)
            .asFlow()
    }

    private fun Query.filterByType(type: TransactionCategoryType?): Query {
        return if (type == null) this else whereEqualTo(FIELD_TYPE, typeMapper.mapFrom(type))
    }

    private fun Query.filterByUser(userId: Id?): Query {
        return if (userId == null) this else this.whereEqualTo(FIELD_OWNER_ID, userId.value)
    }

    private fun mergeDocuments(
        list1: List<DocumentSnapshot>,
        list2: List<DocumentSnapshot>
    ): List<DocumentSnapshot> {
        return list1.plus(list2)
    }

}