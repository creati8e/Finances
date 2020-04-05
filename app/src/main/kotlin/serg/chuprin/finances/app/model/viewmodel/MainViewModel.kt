package serg.chuprin.finances.app.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import serg.chuprin.finances.app.model.AppLaunchState
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.gateway.AuthenticationGateway
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@ScreenScope
class MainViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val authenticationGateway: AuthenticationGateway
) : ViewModel() {

    val userAuthorizedLiveData = liveData {
        val isAuthenticated = authenticationGateway.isAuthenticated()
        if (isAuthenticated) {
            if (onboardingRepository.isOnboardingCompleted()) {
                emit(AppLaunchState.DASHBOARD)
            } else {
                emit(AppLaunchState.ONBOARDING)
            }
        } else {
            emit(AppLaunchState.AUTHENTICATION)
        }
    }

}