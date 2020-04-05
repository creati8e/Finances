package serg.chuprin.finances.core.impl.data.gateway

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import serg.chuprin.finances.core.api.domain.gateway.AuthenticationGateway
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.impl.data.mapper.FirebaseUserMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
internal class AuthenticationGatewayImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userMapper: FirebaseUserMapper
) : AuthenticationGateway {

    override suspend fun isAuthenticated(): Boolean = firebaseAuth.currentUser != null

    override suspend fun signIn(idToken: String): User? {
        return firebaseAuth
            .signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
            .runCatching { await() }
            .getOrNull()
            ?.user
            ?.let(userMapper)
    }

}