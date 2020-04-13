package serg.chuprin.finances.core.impl.data.database.firebase.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_DISPLAY_NAME
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_EMAIL
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_PHOTO_URL
import serg.chuprin.finances.core.impl.data.database.firebase.suspending
import javax.inject.Inject
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class FirebaseUserDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun createAndSetUser(firebaseUser: FirebaseUser): Result<Boolean> {
        return coroutineScope {
            try {
                val fieldsMap = requireNotNull(firebaseUser.toMap()) {
                    "Mapping firebase user to user failed"
                }
                val document = firestore
                    .collection(COLLECTION_NAME)
                    .document(firebaseUser.uid)
                val userIsNew = document.get().await() == null
                document.set(fieldsMap).await()
                success(userIsNew)
            } catch (throwable: Throwable) {
                failure<Boolean>(throwable)
            }
        }
    }

    fun currentUserSingleFlow(): Flow<DocumentSnapshot> {
        return callbackFlow {
            val currentUser = requireNotNull(firebaseAuth.currentUser) {
                "Current user does not exist"
            }
            firestore
                .collection(COLLECTION_NAME)
                .document(currentUser.uid)
                .suspending(this, mapper = { documentSnapshot -> documentSnapshot })
        }
    }

    private fun FirebaseUser.toMap(): Map<String, Any>? {
        if (email.isNullOrEmpty()) {
            return null
        }
        return mapOf(
            FIELD_EMAIL to email.orEmpty(),
            FIELD_DISPLAY_NAME to displayName.orEmpty(),
            FIELD_PHOTO_URL to photoUrl?.toString().orEmpty()
        )
    }

}