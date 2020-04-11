package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.di

import dagger.Subcomponent
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.viewmodel.CurrencyChoiceOnboardingViewModel

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
@ScreenScope
@Subcomponent(
    modules = [CurrencyChoiceOnboardingModule::class]
)
interface CurrencyChoiceOnboardingComponent : ViewModelComponent<CurrencyChoiceOnboardingViewModel>