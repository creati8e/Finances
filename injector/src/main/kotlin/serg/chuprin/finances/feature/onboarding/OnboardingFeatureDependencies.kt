package serg.chuprin.finances.feature.onboarding

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreNavigationProvider
import serg.chuprin.finances.core.api.di.provider.CoreRepositoriesProvider
import serg.chuprin.finances.core.api.di.provider.CoreUseCasesProvider
import serg.chuprin.finances.core.api.domain.repository.CurrencyRepository
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.domain.usecase.SearchCurrenciesUseCase
import serg.chuprin.finances.core.api.presentation.navigation.OnboardingNavigation

/**
 * Created by Sergey Chuprin on 09.04.2020.
 */
interface OnboardingFeatureDependencies {

    // region Repositories.

    val currencyRepository: CurrencyRepository
    val onboardingRepository: OnboardingRepository
    val onboardingNavigation: OnboardingNavigation

    // endregion

    // region Use cases.

    val searchCurrenciesUseCase: SearchCurrenciesUseCase

    // endregion
}

@Component(
    dependencies = [
        CoreUseCasesProvider::class,
        CoreNavigationProvider::class,
        CoreRepositoriesProvider::class
    ]
)
internal interface OnboardingFeatureDependenciesComponent : OnboardingFeatureDependencies