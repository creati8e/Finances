package serg.chuprin.finances.feature.authorization.domain.usecase

import kotlinx.coroutines.coroutineScope
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.model.SignInResult
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val onboardingRepository: OnboardingRepository,
    private val authorizationGateway: AuthorizationGateway
) {

    suspend fun execute(idToken: String): SignInResult {
        return coroutineScope {
            val user = authorizationGateway.signIn(idToken)
            if (user != null) {
                val userIsNew = userRepository.createAndSet(user)
                if (!userIsNew) {
                    onboardingRepository.onboardingStep = OnboardingStep.COMPLETED
                }
                SignInResult.Success(userIsNew = userIsNew)
            } else {
                SignInResult.Error
            }
        }

    }

}