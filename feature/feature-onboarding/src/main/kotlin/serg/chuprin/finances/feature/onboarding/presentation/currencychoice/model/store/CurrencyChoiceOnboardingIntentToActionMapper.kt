package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.store

import serg.chuprin.finances.core.api.presentation.model.mvi.mapper.StoreIntentToActionMapper

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
class CurrencyChoiceOnboardingIntentToActionMapper :
    StoreIntentToActionMapper<CurrencyChoiceOnboardingIntent, CurrencyChoiceOnboardingAction> {

    override fun invoke(intent: CurrencyChoiceOnboardingIntent): CurrencyChoiceOnboardingAction {
        return CurrencyChoiceOnboardingAction.ExecuteIntent(intent)
    }

}