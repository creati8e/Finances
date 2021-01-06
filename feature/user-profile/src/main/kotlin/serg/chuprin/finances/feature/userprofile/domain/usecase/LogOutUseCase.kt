package serg.chuprin.finances.feature.userprofile.domain.usecase

import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.repository.DataRepository
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
class LogOutUseCase @Inject constructor(
    private val dataRepository: DataRepository,
    private val authorizationGateway: AuthorizationGateway,
    private val onboardingRepository: OnboardingRepository
) {

    suspend fun execute() {
        authorizationGateway.logOut()
        dataRepository.clear()
        onboardingRepository.onboardingStep = OnboardingStep.INITIAL
    }

}