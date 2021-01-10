package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch
import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 25.12.2020.
 */
internal abstract class BaseFirebaseDataSource(
    protected val firestore: FirebaseFirestore
) {

    protected abstract val collection: CollectionReference

    fun createOrUpdate(models: Map<Id, Any>) {
        runBatch(models.entries) { collection, (id, fields) ->
            set(collection.document(id.value), fields)
        }
    }

    fun delete(ids: Collection<Id>) {
        runBatch(ids) { collection, id ->
            delete(collection.document(id.value))
        }
    }

    private fun <T> runBatch(
        models: Collection<T>,
        operation: WriteBatch.(CollectionReference, T) -> Unit
    ) {
        val collection = collection
        models
            // Firebase does not support batch operations with more than 500 documents in one batch.
            .windowed(size = 499, step = 499, partialWindows = true)
            .forEach { documentsPart ->
                firestore
                    .runBatch { writeBatch ->
                        documentsPart.forEach { model ->
                            operation(writeBatch, collection, model)
                        }
                    }
            }
    }

}