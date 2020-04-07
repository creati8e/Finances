package serg.chuprin.finances.feature.onboarding

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreNavigationProvider
import serg.chuprin.finances.core.api.di.provider.CoreRepositoriesProvider
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.presentation.navigation.OnboardingNavigation

/**
 * Created by Sergey Chuprin on 06.04.2020.
 */
interface OnboardingDependencies {
    val onboardingRepository: OnboardingRepository
    val onboardingNavigation: OnboardingNavigation
}

@Component(
    dependencies = [
        CoreNavigationProvider::class,
        CoreRepositoriesProvider::class
    ]
)
internal interface OnboardingDependenciesComponent : OnboardingDependencies