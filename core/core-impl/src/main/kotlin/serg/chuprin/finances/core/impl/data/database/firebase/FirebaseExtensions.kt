package serg.chuprin.finances.core.impl.data.database.firebase

import com.google.firebase.firestore.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal suspend fun <T> DocumentReference.suspending(
    producerScope: ProducerScope<T>,
    mapper: (DocumentSnapshot) -> T
) {
    val listener = addSnapshotListener { querySnapshot: DocumentSnapshot?,
                                         exception: FirebaseFirestoreException? ->
        exception?.let(producerScope::close)
            ?: querySnapshot?.let { producerScope.offer(mapper(it)) }
    }
    producerScope.awaitClose { listener.remove() }
}

internal suspend fun <T> Query.suspending(
    producerScope: ProducerScope<T>,
    mapper: (QuerySnapshot) -> T
) {
    val listener = addSnapshotListener { querySnapshot: QuerySnapshot?,
                                         exception: FirebaseFirestoreException? ->
        exception?.let(producerScope::close)
            ?: querySnapshot?.let { producerScope.offer(mapper(it)) }
    }
    producerScope.awaitClose { listener.remove() }
}