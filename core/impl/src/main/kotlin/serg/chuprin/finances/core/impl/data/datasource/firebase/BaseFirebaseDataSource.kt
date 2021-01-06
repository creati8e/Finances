package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 25.12.2020.
 */
abstract class BaseFirebaseDataSource(
    protected val firestore: FirebaseFirestore
) {

    protected abstract val collection: CollectionReference

    suspend fun delete(ids: Collection<Id>) {
        coroutineScope {
            Timber.d { "Trying to delete batch: ${ids.size}" }
            val collection = collection
            ids
                // Firebase does not support deletion more than 500 documents in one batch.
                .windowed(size = 499, step = 499, partialWindows = true)
                .map { documentsPart ->
                    Timber.d { "Trying to delete part of batch: ${documentsPart.size}" }
                    async {
                        firestore
                            .runBatch { writeBatch ->
                                documentsPart.forEach { id ->
                                    writeBatch.delete(collection.document(id.value))
                                }
                            }.awaitWithLogging {
                                "Unable to delete documents"
                            }
                    }
                }
                .forEach { deferred -> deferred.await() }
        }
    }

}