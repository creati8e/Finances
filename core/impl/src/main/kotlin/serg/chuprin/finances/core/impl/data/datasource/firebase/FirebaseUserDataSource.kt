package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.extensions.nonNullValuesMap
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_DATA_PERIOD_TYPE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_DEFAULT_CURRENCY_CODE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_DISPLAY_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_EMAIL
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_PHOTO_URL
import serg.chuprin.finances.core.impl.data.mapper.FirebaseDataPeriodTypeMapper
import serg.chuprin.finances.core.impl.data.mapper.user.FirebaseUserMapper
import javax.inject.Inject
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class FirebaseUserDataSource @Inject constructor(
    private val userMapper: FirebaseUserMapper,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dataPeriodTypeMapper: FirebaseDataPeriodTypeMapper
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
                val document = getCollection().document(firebaseUser.uid)
                val documentSnapshot = document.get().await()

                // Currency code field is fine for detecting whether user is new or not
                // because it's set only after onboarding flow completion.
                val userIsNew = !documentSnapshot.exists()
                        || documentSnapshot.getString(FIELD_DEFAULT_CURRENCY_CODE).isNullOrEmpty()
                document
                    .set(fieldsMap, SetOptions.merge())
                    .await()
                success(userIsNew)
            } catch (throwable: Throwable) {
                failure(throwable)
            }
        }
    }

    fun updateUser(user: User) {
        getCurrentUserDocument().set(userMapper.mapToFieldsMap(user))
    }

    suspend fun getCurrentUser(): DocumentSnapshot = internalGetCurrentUser()

    suspend fun getIncompleteUser(): DocumentSnapshot = internalGetCurrentUser()

    fun currentUserSingleFlow(): Flow<DocumentSnapshot> {
        return getCurrentUserDocument().asFlow()
    }

    private suspend fun internalGetCurrentUser(): DocumentSnapshot {
        return getCurrentUserDocument()
            .get(Source.CACHE)
            .awaitWithLogging {
                "An error occurring when getting user"
            }!!
    }

    private fun FirebaseUser.toMap(): Map<String, Any>? {
        if (email.isNullOrEmpty()) {
            return null
        }
        return nonNullValuesMap(
            FIELD_EMAIL to email.orEmpty(),
            FIELD_DISPLAY_NAME to displayName.orEmpty(),
            FIELD_PHOTO_URL to photoUrl?.toString().orEmpty(),
            FIELD_DATA_PERIOD_TYPE to dataPeriodTypeMapper.mapFrom(DataPeriodType.DEFAULT)
        )
    }

    private fun getCurrentUserDocument(): DocumentReference {
        return getCollection().document(currentFirebaseUser.uid)
    }

    private fun getCollection(): CollectionReference = firestore.collection(COLLECTION_NAME)

}