package serg.chuprin.finances.core.impl.data.repository

import serg.chuprin.finances.core.api.data.datasource.preferences.Preference
import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesAdapter
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.impl.data.preferences.EnumPreferenceMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
internal class OnboardingRepositoryImpl @Inject constructor(
    preferencesAdapter: PreferencesAdapter
) : OnboardingRepository {

    private class OnboardingStepPreferenceMapper : EnumPreferenceMapper<OnboardingStep>() {

        override val preferenceValuesMap: Map<OnboardingStep, String> = mapOf(
            OnboardingStep.COMPLETED to "completed",
            OnboardingStep.ACCOUNT_SETUP to "account_setup",
            OnboardingStep.CURRENCY_CHOICE to "currency_choice"
        )

        override val defaultModel = OnboardingStep.CURRENCY_CHOICE

    }

    private val preference: Preference<OnboardingStep> = preferencesAdapter.getCustomModel(
        key = "onboarding_step",
        mapper = OnboardingStepPreferenceMapper()
    )

    override val onboardingStep: OnboardingStep
        get() = preference.value

}