package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 25.12.2020.
 */
abstract class BaseFirebaseDataSource(
    protected val firestore: FirebaseFirestore
) {

    protected abstract val collection: CollectionReference

    fun delete(ids: Collection<Id>) {
        Timber.d { "Trying to delete batch: ${ids.size}" }
        val collection = collection
        ids
            // Firebase does not support deletion more than 500 documents in one batch.
            .windowed(size = 499, step = 499, partialWindows = true)
            .forEach { documentsPart ->
                Timber.d { "Trying to delete part of batch: ${documentsPart.size}" }
                firestore
                    .runBatch { writeBatch ->
                        documentsPart.forEach { id ->
                            writeBatch.delete(collection.document(id.value))
                        }
                    }
            }
    }

}