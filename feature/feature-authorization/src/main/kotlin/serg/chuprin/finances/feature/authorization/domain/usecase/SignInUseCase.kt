package serg.chuprin.finances.feature.authorization.domain.usecase

import kotlinx.coroutines.coroutineScope
import serg.chuprin.finances.core.api.domain.gateway.AuthenticationGateway
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationGateway: AuthenticationGateway
) {

    suspend fun execute(idToken: String): Boolean {
        return coroutineScope {
            val user = authenticationGateway.signIn(idToken)
            if (user != null) {
                userRepository.setCurrentUser(user)
            }
            user != null
        }

    }

}