package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery.Relation
import serg.chuprin.finances.core.api.extensions.contains
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseCategoryFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseCategoryFieldsContract.FIELD_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseCategoryFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseCategoryFieldsContract.FIELD_PARENT_ID
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseCategoryFieldsContract.FIELD_TYPE
import serg.chuprin.finances.core.impl.data.mapper.category.FirebaseCategoryTypeMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
internal class FirebaseCategoryQueryExecutor @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val typeMapper: FirebaseCategoryTypeMapper
) {

    fun execute(query: CategoriesQuery): Flow<List<DocumentSnapshot>> {
        return when (query.relation) {
            Relation.RETRIEVE_CHILDREN -> {
                retrieveChildrenCategoriesAndMerge(query)
            }
            Relation.RETRIEVE_PARENTS -> {
                retrieveParentCategoriesAndMerge(query)
            }
            null -> {
                buildQueryFlow(query).map { documents ->
                    if (query.categoryIds.isEmpty()) {
                        documents
                    } else {
                        documents.filter { document ->
                            query.categoryIds.contains { id -> id.value == document.id }
                        }
                    }
                }
            }
        }
    }

    private fun retrieveParentCategoriesAndMerge(
        query: CategoriesQuery
    ): Flow<List<DocumentSnapshot>> {
        return flow {
            coroutineScope {
                val sharedCategoriesFlow = buildQueryFlow(query)
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
        query: CategoriesQuery
    ): Flow<List<DocumentSnapshot>> {
        return flow {
            coroutineScope {
                val sharedCategoriesFlow = buildQueryFlow(query).shareIn(
                    scope = this,
                    started = SharingStarted.WhileSubscribed(replayExpirationMillis = 0),
                )

                val parentCategoriesFlow = sharedCategoriesFlow.map { documents ->
                    if (query.categoryIds.isEmpty()) {
                        documents
                    } else {
                        documents.filter { document ->
                            query.categoryIds.contains { id -> id.value == document.id }
                        }
                    }
                }

                val childrenCategoriesFlow = sharedCategoriesFlow.map { documents ->
                    if (query.categoryIds.isEmpty()) {
                        documents
                    } else {
                        documents.filter { document ->
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

    private fun buildQueryFlow(query: CategoriesQuery): Flow<List<DocumentSnapshot>> {
        return firestore
            .collection(COLLECTION_NAME)
            .filterByOwner(query.ownerId)
            .filterByType(query.type)
            .asFlow()
            .filterByName(query.searchByName)
    }

    private fun Query.filterByType(type: CategoryType?): Query {
        return if (type == null) this else whereEqualTo(FIELD_TYPE, typeMapper.mapFrom(type))
    }

    private fun Flow<QuerySnapshot>.filterByName(name: String?): Flow<List<DocumentSnapshot>> {
        if (name == null) {
            return map { it.documents }
        }
        return map { querySnapshot ->
            querySnapshot.documents.filter { documentSnapshot ->
                documentSnapshot.getString(FIELD_NAME).orEmpty().contains(name, true)
            }
        }
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