package serg.chuprin.finances.app.launcher.authorized

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.05.2020.
 */
@ScreenScope
class AuthorizationGraphLauncherViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {

    val isOnboardingCompletedLiveData = liveData {
        emit(onboardingRepository.onboardingStep == OnboardingStep.COMPLETED)
    }

}