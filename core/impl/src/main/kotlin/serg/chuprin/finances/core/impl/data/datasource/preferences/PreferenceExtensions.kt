package serg.chuprin.finances.core.impl.data.datasource.preferences

import com.afollestad.rxkprefs.Pref
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
internal fun <T : Any> Pref<T>.asFlow(): Flow<T> {
    return callbackFlow {
        addOnDestroyed { close() }
        addOnChanged {
            if (isActive) {
                offer(get())
            }
        }
        offer(get())
        awaitClose { cancel() }
    }
}