package serg.chuprin.finances.core.impl.data.mapper

import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_DISPLAY_NAME
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_EMAIL
import serg.chuprin.finances.core.impl.data.database.firebase.contract.FirebaseUserFieldsContract.FIELD_PHOTO_URL
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
internal class UserMapper @Inject constructor() : ModelMapper<DocumentSnapshot, User> {

    override fun invoke(documentSnapshot: DocumentSnapshot): User? {
        val email = documentSnapshot.getString(FIELD_EMAIL) ?: return null
        val displayName = documentSnapshot.getString(FIELD_DISPLAY_NAME) ?: return null
        val photoUrl = documentSnapshot.getString(FIELD_PHOTO_URL).orEmpty()
        return User(
            email = email,
            photoUrl = photoUrl,
            displayName = displayName,
            id = Id(documentSnapshot.id)
        )
    }

}