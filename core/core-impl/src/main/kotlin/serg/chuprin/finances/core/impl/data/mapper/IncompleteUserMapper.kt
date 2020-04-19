package serg.chuprin.finances.core.impl.data.mapper

import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.IncompleteUser
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseUserFieldsContract
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
internal class IncompleteUserMapper @Inject constructor(
    private val dataPeriodTypeMapper: DataPeriodTypeMapper
) : ModelMapper<DocumentSnapshot, IncompleteUser> {

    override fun invoke(documentSnapshot: DocumentSnapshot): IncompleteUser? {
        return try {
            IncompleteUser.create(
                id = documentSnapshot.id,
                dataPeriodType = dataPeriodTypeMapper(documentSnapshot),
                email = documentSnapshot.getString(FirebaseUserFieldsContract.FIELD_EMAIL),
                photoUrl = documentSnapshot.getString(FirebaseUserFieldsContract.FIELD_PHOTO_URL),
                displayName = documentSnapshot.getString(FirebaseUserFieldsContract.FIELD_DISPLAY_NAME)
            )
        } catch (throwable: Throwable) {
            Timber.d(throwable) { "An error occurred when mapping user" }
            null
        }
    }

}