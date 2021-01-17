package serg.chuprin.finances.feature.onboarding.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.FeatureScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.onboarding.di.accountssetup.AccountsSetupOnboardingComponent
import serg.chuprin.finances.feature.onboarding.di.currencychoice.CurrencyChoiceOnboardingComponent
import serg.chuprin.finances.feature.onboarding.presentation.launch.model.viewmodel.OnboardingLaunchViewModel
import serg.chuprin.finances.feature.onboarding.presentation.launch.view.OnboardingLaunchFragment

/**
 * Created by Sergey Chuprin on 09.04.2020.
 */
@[FeatureScope Component(dependencies = [OnboardingFeatureDependencies::class])]
interface OnboardingFeatureComponent :
    ViewModelComponent<OnboardingLaunchViewModel>,
    InjectableComponent<OnboardingLaunchFragment> {

    companion object {

        fun get(dependencies: OnboardingFeatureDependencies): OnboardingFeatureComponent {
            return DaggerOnboardingFeatureComponent.builder()
                .onboardingFeatureDependencies(dependencies)
                .build()
        }

    }

    fun accountsSetupComponent(): AccountsSetupOnboardingComponent

    fun currencyChoiceComponent(): CurrencyChoiceOnboardingComponent

}