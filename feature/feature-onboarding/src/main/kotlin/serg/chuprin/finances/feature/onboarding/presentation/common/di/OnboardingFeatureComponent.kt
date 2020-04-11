package serg.chuprin.finances.feature.onboarding.presentation.common.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.FeatureScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.onboarding.OnboardingFeatureDependencies
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.di.AccountsSetupOnboardingComponent
import serg.chuprin.finances.feature.onboarding.presentation.common.model.viewmodel.OnboardingContainerViewModel
import serg.chuprin.finances.feature.onboarding.presentation.common.view.OnboardingContainerFragment
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.di.CurrencyChoiceOnboardingComponent
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 09.04.2020.
 */
@FeatureScope
@Component(dependencies = [OnboardingFeatureDependencies::class])
interface OnboardingFeatureComponent : ViewModelComponent<OnboardingContainerViewModel> {

    companion object {

        fun get(): OnboardingFeatureComponent {
            return DaggerOnboardingFeatureComponent
                .builder()
                .onboardingFeatureDependencies(Injector.getOnboardingFeatureDependencies())
                .build()
        }

    }

    fun inject(fragment: OnboardingContainerFragment)

    fun accountsSetupComponent(): AccountsSetupOnboardingComponent

    fun currencyChoiceComponent(): CurrencyChoiceOnboardingComponent

}