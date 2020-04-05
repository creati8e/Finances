package serg.chuprin.finances.core.impl.data.repository

import serg.chuprin.finances.core.api.data.datasource.preferences.PreferencesAdapter
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
internal class OnboardingRepositoryImpl @Inject constructor(
    preferencesAdapter: PreferencesAdapter
) : OnboardingRepository {

    private val preference = preferencesAdapter.getBoolean("is_onboarding_completed", false)

    override fun isOnboardingCompleted(): Boolean {
        return preference.value
    }

}