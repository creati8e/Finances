package serg.chuprin.finances.feature.authorization.domain.usecase

import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.model.SignInResult
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
class SignInUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val authorizationGateway: AuthorizationGateway
) {

    suspend fun execute(idToken: String): SignInResult {
        return authorizationGateway
            .signIn(idToken)
            .fold(
                { userIsNew ->
                    if (!userIsNew) {
                        onboardingRepository.onboardingStep = OnboardingStep.COMPLETED
                    }
                    SignInResult.Success
                },
                { SignInResult.Error }
            )
    }

}