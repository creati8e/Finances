package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.category.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.category.query.TransactionCategoriesQuery.Relation
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
                retrieveChildrenCategoriesAndMerge(query)
            }
            Relation.RETRIEVE_PARENTS -> {
                retrieveParentCategoriesAndMerge(query)
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

    private fun retrieveParentCategoriesAndMerge(
        query: TransactionCategoriesQuery
    ): Flow<List<DocumentSnapshot>> {
        return flow {
            coroutineScope {
                val sharedCategoriesFlow = buildQueryFlow(query)
                    .map { it.documents }
                    .shareIn(
                        scope = this,
                        started = SharingStarted.WhileSubscribed(replayExpirationMillis = 0),
                    )

                val childrenCategoriesFlow = sharedCategoriesFlow.map { documents ->
                    if (query.categoryIds.isEmpty()) {
                        documents
                    } else {
                        documents.filter { document ->
                            query.categoryIds.contains { id -> id.value == document.id }
                        }
                    }
                }

                val parentCategoriesFlow = sharedCategoriesFlow
                    .zip(childrenCategoriesFlow) { allDocuments, childrenDocuments ->
                        val parentCategoryIds = childrenDocuments.mapTo(
                            mutableSetOf(),
                            { document -> document.getString(FIELD_PARENT_ID) }
                        )
                        allDocuments.filter { document ->
                            parentCategoryIds.contains { id -> id == document.id }
                        }
                    }

                emitAll(combine(parentCategoriesFlow, childrenCategoriesFlow, ::mergeDocuments))
            }
        }
    }

    private fun retrieveChildrenCategoriesAndMerge(
        query: TransactionCategoriesQuery
    ): Flow<List<DocumentSnapshot>> {
        return flow {
            coroutineScope {
                val sharedCategoriesFlow = buildQueryFlow(query).shareIn(
                    scope = this,
                    started = SharingStarted.WhileSubscribed(replayExpirationMillis = 0),
                )

                val parentCategoriesFlow = sharedCategoriesFlow.map { querySnapshot ->
                    if (query.categoryIds.isEmpty()) {
                        querySnapshot.documents
                    } else {
                        querySnapshot.documents.filter { document ->
                            query.categoryIds.contains { id -> id.value == document.id }
                        }
                    }
                }

                val childrenCategoriesFlow = sharedCategoriesFlow.map { querySnapshot ->
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
            .filterByOwner(query.ownerId)
            .filterByType(query.type)
            .filterByName(query.searchByName)
            .asFlow()
    }

    private fun Query.filterByType(type: TransactionCategoryType?): Query {
        return if (type == null) this else whereEqualTo(FIELD_TYPE, typeMapper.mapFrom(type))
    }

    private fun Query.filterByName(name: String?): Query {
        if (name == null) {
            return this
        }
        val strLength = name.length
        val strFrontCode = name.slice(0 until strLength)
        val strEndCode = name.slice(strLength - 1..name.length)

        val endCode = strFrontCode + (strEndCode[0] + 1).toString()

        return whereGreaterThanOrEqualTo("foo", name)
            .whereLessThan("foo", endCode)
    }

    private fun Query.filterByOwner(ownerId: Id?): Query {
        return if (ownerId == null) this else this.whereEqualTo(FIELD_OWNER_ID, ownerId.value)
    }

    private fun mergeDocuments(
        list1: List<DocumentSnapshot>,
        list2: List<DocumentSnapshot>
    ): List<DocumentSnapshot> {
        return list1.plus(list2)
    }

}