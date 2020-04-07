package serg.chuprin.finances.app.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import serg.chuprin.finances.app.model.AppLaunchState
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@ScreenScope
class MainViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val authorizationGateway: AuthorizationGateway
) : ViewModel() {

    val userAuthorizedLiveData = liveData {
        val isAuthorized = authorizationGateway.isAuthorized()
        if (isAuthorized) {
            if (onboardingRepository.onboardingStep == OnboardingStep.COMPLETED) {
                emit(AppLaunchState.DASHBOARD)
            } else {
                emit(AppLaunchState.ONBOARDING)
            }
        } else {
            emit(AppLaunchState.AUTHORIZATION)
        }
    }

}