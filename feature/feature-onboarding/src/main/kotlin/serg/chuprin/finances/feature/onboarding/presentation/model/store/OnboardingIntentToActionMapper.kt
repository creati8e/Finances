package serg.chuprin.finances.feature.onboarding.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.mvi.mapper.StoreIntentToActionMapper

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class OnboardingIntentToActionMapper :
    StoreIntentToActionMapper<OnboardingIntent, OnboardingAction> {

    override fun invoke(intent: OnboardingIntent): OnboardingAction {
        return OnboardingAction.ExecuteIntent(intent)
    }

}