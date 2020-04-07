package serg.chuprin.finances.feature.onboarding.presentation.model.store

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
sealed class OnboardingAction {

    class SetInitialStepState(
        val stepState: OnboardingStepState
    ) : OnboardingAction()

    class ExecuteIntent(
        val intent: OnboardingIntent
    ) : OnboardingAction()

}