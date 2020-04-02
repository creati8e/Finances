package serg.chuprin.finances.core.impl.data.gateway

import com.google.firebase.auth.FirebaseAuth
import serg.chuprin.finances.core.api.domain.gateway.AuthenticationGateway
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.impl.data.mapper.UserMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
class AuthenticationGatewayImpl @Inject constructor(
    private val userMapper: UserMapper
) : AuthenticationGateway {

    override suspend fun getCurrentUser(): User? {
        return FirebaseAuth.getInstance().currentUser?.let(userMapper)
    }

}