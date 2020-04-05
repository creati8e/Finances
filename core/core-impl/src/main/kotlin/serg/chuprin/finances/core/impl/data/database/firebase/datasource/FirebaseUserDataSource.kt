package serg.chuprin.finances.core.impl.data.database.firebase.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_DISPLAY_NAME
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_EMAIL
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_PHOTO_URL
import serg.chuprin.finances.core.impl.data.database.firebase.suspending
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class FirebaseUserDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    private companion object {
        private const val COLLECTION_NAME = "user"
    }

    suspend fun setCurrentUser(user: User) {
        val userStructure = mapOf(
            FIELD_EMAIL to user.email,
            FIELD_PHOTO_URL to user.photoUrl,
            FIELD_DISPLAY_NAME to user.displayName
        )
        firestore
            .collection(COLLECTION_NAME)
            .document(user.id.value)
            .set(userStructure)
            .await()
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

}