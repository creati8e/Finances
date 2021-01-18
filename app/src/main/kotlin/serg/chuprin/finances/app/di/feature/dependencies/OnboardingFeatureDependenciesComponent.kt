package serg.chuprin.finances.app.di.feature.dependencies

import dagger.Component
import serg.chuprin.finances.app.di.navigation.AppNavigationProvider
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.currency.choice.api.di.CurrencyChoiceStoreApi
import serg.chuprin.finances.feature.onboarding.di.OnboardingFeatureDependencies

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
@Component(
    dependencies = [
        AppNavigationProvider::class,
        CurrencyChoiceStoreApi::class,
        CoreDependenciesProvider::class
    ]
)
internal interface OnboardingFeatureDependenciesComponent : OnboardingFeatureDependencies