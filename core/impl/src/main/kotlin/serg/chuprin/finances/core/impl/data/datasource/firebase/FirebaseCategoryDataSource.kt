package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseCategoryFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.mapper.category.FirebaseCategoryMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class FirebaseCategoryDataSource @Inject constructor(
    firestore: FirebaseFirestore,
    private val mapper: FirebaseCategoryMapper,
    private val queryExecutor: FirebaseCategoryQueryExecutor
) : BaseFirebaseDataSource(firestore) {

    override val collection: CollectionReference
        get() = firestore.collection(COLLECTION_NAME)

    fun createCategories(categories: List<Category>) {
        val collection = collection
        firestore.runBatch { writeBatch ->
            categories.forEach { category ->
                writeBatch.set(
                    collection.document(category.id.value),
                    mapper.mapToFieldsMap(category)
                )
            }
        }
    }

    fun deleteCategories(categories: List<Category>) {
        delete(categories.map(Category::id))
    }

    fun categoriesFlow(query: CategoriesQuery): Flow<List<DocumentSnapshot>> {
        return queryExecutor.execute(query)
    }

}