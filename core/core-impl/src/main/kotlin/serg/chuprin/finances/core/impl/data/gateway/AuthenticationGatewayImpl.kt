package serg.chuprin.finances.core.impl.data.gateway

import com.google.firebase.auth.FirebaseAuth
import serg.chuprin.finances.core.api.domain.gateway.AuthenticationGateway
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
internal class AuthenticationGatewayImpl @Inject constructor() : AuthenticationGateway {

    override suspend fun isAuthenticated(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

}