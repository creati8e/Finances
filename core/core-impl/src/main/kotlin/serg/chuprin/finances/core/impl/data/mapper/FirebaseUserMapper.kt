package serg.chuprin.finances.core.impl.data.mapper

import com.google.firebase.auth.FirebaseUser
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.User
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal class FirebaseUserMapper @Inject constructor() : ModelMapper<FirebaseUser, User> {

    override fun invoke(firebaseUser: FirebaseUser): User? {
        if (firebaseUser.email.isNullOrEmpty()) {
            return null
        }
        if (firebaseUser.displayName.isNullOrEmpty()) {
            return null
        }
        return User(
            id = Id(firebaseUser.uid),
            email = firebaseUser.email.orEmpty(),
            displayName = firebaseUser.displayName.orEmpty(),
            photoUrl = firebaseUser.photoUrl?.toString().orEmpty()
        )
    }

}