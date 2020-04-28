package serg.chuprin.finances.feature.onboarding.presentation.launch.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.FeatureScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.onboarding.dependencies.OnboardingFeatureDependencies
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.di.AccountsSetupOnboardingComponent
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.di.CurrencyChoiceOnboardingComponent
import serg.chuprin.finances.feature.onboarding.presentation.launch.model.viewmodel.OnboardingLaunchViewModel
import serg.chuprin.finances.feature.onboarding.presentation.launch.view.OnboardingLaunchFragment
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 09.04.2020.
 */
@FeatureScope
@Component(dependencies = [OnboardingFeatureDependencies::class])
interface OnboardingFeatureComponent : ViewModelComponent<OnboardingLaunchViewModel> {

    companion object {

        fun get(): OnboardingFeatureComponent {
            return DaggerOnboardingFeatureComponent
                .builder()
                .onboardingFeatureDependencies(Injector.getOnboardingFeatureDependencies())
                .build()
        }

    }

    fun inject(fragment: OnboardingLaunchFragment)

    fun accountsSetupComponent(): AccountsSetupOnboardingComponent

    fun currencyChoiceComponent(): CurrencyChoiceOnboardingComponent

}