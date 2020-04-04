package serg.chuprin.finances.core.impl.data.mapper

import com.google.firebase.auth.FirebaseUser
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.User
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
class UserMapper @Inject constructor() : ModelMapper<FirebaseUser, User> {

    override fun invoke(firebaseUser: FirebaseUser): User? {
        return User(id = Id(firebaseUser.uid), email = firebaseUser.email.orEmpty())
    }

}