package serg.chuprin.finances.feature.onboarding.presentation.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.feature.onboarding.OnboardingDependencies
import serg.chuprin.finances.feature.onboarding.presentation.model.viewmodel.CurrencyChoiceOnboardingViewModel
import serg.chuprin.finances.feature.onboarding.presentation.model.viewmodel.OnboardingContainerViewModel
import serg.chuprin.finances.feature.onboarding.presentation.view.OnboardingContainerFragment
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
@ScreenScope
@Component(
    modules = [OnboardingModule::class],
    dependencies = [OnboardingDependencies::class]
)
interface OnboardingComponent {

    companion object {
        fun get(): OnboardingComponent {
            return DaggerOnboardingComponent
                .builder()
                .onboardingDependencies(Injector.getOnboardingDependencies())
                .build()
        }
    }

    val containerViewModel: OnboardingContainerViewModel

    val currencyChoiceViewModel: CurrencyChoiceOnboardingViewModel

    fun inject(fragment: OnboardingContainerFragment)

}