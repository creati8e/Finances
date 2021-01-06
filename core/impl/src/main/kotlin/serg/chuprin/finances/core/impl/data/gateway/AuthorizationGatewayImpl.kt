package serg.chuprin.finances.core.impl.data.gateway

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.impl.data.datasource.firebase.FirebaseUserDataSource
import javax.inject.Inject
import kotlin.Result.Companion.failure

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
internal class AuthorizationGatewayImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataSource: FirebaseUserDataSource
) : AuthorizationGateway {

    private val unknownError: Throwable
        get() = RuntimeException("An unknown error occurred during signing in in Firebase")

    override suspend fun isAuthorized(): Boolean = firebaseAuth.currentUser != null

    override suspend fun signIn(idToken: String): Result<Boolean> {
        return firebaseAuth
            .signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
            .runCatching { await() }
            .map(AuthResult::getUser)
            .fold(
                { user ->
                    if (user != null) {
                        dataSource.createAndSetUser(user)
                    } else {
                        failure(unknownError)
                    }
                },
                { exception -> failure(exception) }
            )
    }

    override suspend fun logOut() {
        coroutineScope {
            firebaseAuth.signOut()
        }
    }

}