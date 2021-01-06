package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.github.ajalt.timberkt.Timber
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

internal fun DocumentReference.asFlow(): Flow<DocumentSnapshot> {
    return callbackFlow {
        val listener = addSnapshotListener { querySnapshot: DocumentSnapshot?,
                                             exception: FirebaseFirestoreException? ->
            exception?.let(::close) ?: querySnapshot?.let(::offer)
        }
        awaitClose { listener.remove() }
    }
}

internal fun Query.asFlow(): Flow<QuerySnapshot> {
    return callbackFlow {
        val listener = addSnapshotListener { querySnapshot: QuerySnapshot?,
                                             exception: FirebaseFirestoreException? ->
            exception?.let(::close) ?: querySnapshot?.let(::offer)
        }
        awaitClose { listener.remove() }
    }
}

internal suspend fun <T> Task<T>.awaitWithLogging(errorMessageProvider: () -> String): T {
    return try {
        await()
    } catch (throwable: Throwable) {
        if (throwable !is CancellationException) {
            Timber.d(throwable) { errorMessageProvider() }
        }
        throw throwable
    }
}