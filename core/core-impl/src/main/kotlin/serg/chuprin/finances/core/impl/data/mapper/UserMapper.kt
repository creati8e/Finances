package serg.chuprin.finances.core.impl.data.mapper

import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_DEFAULT_CURRENCY_CODE
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_DISPLAY_NAME
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_EMAIL
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_PHOTO_URL
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
internal class UserMapper @Inject constructor() : ModelMapper<DocumentSnapshot, User> {

    override fun invoke(documentSnapshot: DocumentSnapshot): User? {
        return try {
            User.create(
                id = documentSnapshot.id,
                email = documentSnapshot.getString(FIELD_EMAIL),
                photoUrl = documentSnapshot.getString(FIELD_PHOTO_URL),
                displayName = documentSnapshot.getString(FIELD_DISPLAY_NAME),
                defaultCurrencyCode = documentSnapshot.getString(FIELD_DEFAULT_CURRENCY_CODE)
            )
        } catch (throwable: Throwable) {
            Timber.d(throwable) { "An error occurred when mapping user" }
            null
        }
    }

}