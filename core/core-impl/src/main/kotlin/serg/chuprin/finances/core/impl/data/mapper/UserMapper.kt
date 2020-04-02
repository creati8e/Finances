package serg.chuprin.finances.core.impl.data.mapper

import com.google.firebase.auth.FirebaseUser
import dagger.Reusable
import serg.chuprin.finances.core.api.domain.model.User
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@Reusable
class UserMapper @Inject constructor() : ModelMapper<FirebaseUser, User> {

    override fun invoke(firebaseUser: FirebaseUser): User? = firebaseUser.email?.let(::User)

}