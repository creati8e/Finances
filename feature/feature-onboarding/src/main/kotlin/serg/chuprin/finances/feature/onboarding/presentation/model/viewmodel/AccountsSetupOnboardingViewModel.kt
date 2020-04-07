package serg.chuprin.finances.feature.onboarding.presentation.model.viewmodel

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingIntent
import serg.chuprin.finances.feature.onboarding.presentation.model.store.OnboardingStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.04.2020.
 */
@ScreenScope
class AccountsSetupOnboardingViewModel @Inject constructor(
    private val store: OnboardingStore
) : BaseStoreViewModel<OnboardingIntent>()