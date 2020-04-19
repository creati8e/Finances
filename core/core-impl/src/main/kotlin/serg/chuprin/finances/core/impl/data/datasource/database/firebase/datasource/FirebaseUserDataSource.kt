package serg.chuprin.finances.core.impl.data.datasource.database.firebase.datasource

import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import serg.chuprin.finances.core.api.domain.model.DataPeriodType
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.awaitWithLogging
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseUserFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseUserFieldsContract.FIELD_DATA_PERIOD_TYPE
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseUserFieldsContract.FIELD_DEFAULT_CURRENCY_CODE
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseUserFieldsContract.FIELD_DISPLAY_NAME
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseUserFieldsContract.FIELD_EMAIL
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseUserFieldsContract.FIELD_PHOTO_URL
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.suspending
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

    private val currentFirebaseUser: FirebaseUser
        get() {
            return requireNotNull(firebaseAuth.currentUser) {
                "Current user does not exist"
            }
        }

    suspend fun createAndSetUser(firebaseUser: FirebaseUser): Result<Boolean> {
        return coroutineScope {
            try {
                val fieldsMap = requireNotNull(firebaseUser.toMap()) {
                    "Mapping firebase user to user failed"
                }
                val document = firestore
                    .collection(COLLECTION_NAME)
                    .document(firebaseUser.uid)
                val documentSnapshot = document.get().await()
                val userIsNew = !documentSnapshot.exists()
                        || documentSnapshot.getString(FIELD_DEFAULT_CURRENCY_CODE).isNullOrEmpty()
                document
                    .set(fieldsMap)
                    .await()
                    .also {
                        Timber.d { "User created" }
                    }
                success(userIsNew)
            } catch (throwable: Throwable) {
                failure<Boolean>(throwable)
            }
        }
    }

    fun updateUser(user: User) {
        firestore
            .collection(COLLECTION_NAME)
            .document(currentFirebaseUser.uid)
            .set(user.toMap())
    }

    suspend fun getCurrentUser(): DocumentSnapshot = internalGetCurrentUser()

    suspend fun getIncompleteUser(): DocumentSnapshot = internalGetCurrentUser()

    fun currentUserSingleFlow(): Flow<DocumentSnapshot> {
        return callbackFlow {
            firestore
                .collection(COLLECTION_NAME)
                .document(currentFirebaseUser.uid)
                .suspending(this, mapper = { documentSnapshot -> documentSnapshot })
        }
    }

    private suspend fun internalGetCurrentUser(): DocumentSnapshot {
        return firestore
            .collection(COLLECTION_NAME)
            .document(currentFirebaseUser.uid)
            .get(Source.CACHE)
            .awaitWithLogging {
                "An error occurring when getting user"
            }!!
    }

    private fun User.toMap(): Map<String, Any> {
        return mapOf(
            FIELD_EMAIL to email,
            FIELD_PHOTO_URL to photoUrl,
            FIELD_DISPLAY_NAME to displayName,
            FIELD_DATA_PERIOD_TYPE to dataPeriodType.toValue(),
            FIELD_DEFAULT_CURRENCY_CODE to defaultCurrencyCode
        )
    }

    private fun FirebaseUser.toMap(): Map<String, Any>? {
        if (email.isNullOrEmpty()) {
            return null
        }
        return mapOf(
            FIELD_EMAIL to email.orEmpty(),
            FIELD_DISPLAY_NAME to displayName.orEmpty(),
            FIELD_PHOTO_URL to photoUrl?.toString().orEmpty(),
            FIELD_DATA_PERIOD_TYPE to DataPeriodType.DEFAULT.toValue()
        )
    }

    private fun DataPeriodType.toValue(): String {
        return when (this) {
            DataPeriodType.MONTH -> "month"
        }
    }

}