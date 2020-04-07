package serg.chuprin.finances.feature.onboarding.presentation.model.store

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
data class OnboardingState(
    // Null because initial step should be fetched.
    val stepState: OnboardingStepState? = null
)