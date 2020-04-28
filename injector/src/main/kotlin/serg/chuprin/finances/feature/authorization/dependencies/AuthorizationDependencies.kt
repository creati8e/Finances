package serg.chuprin.finances.feature.authorization.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.presentation.navigation.AuthorizationNavigation

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface AuthorizationDependencies {
    val authorizationGateway: AuthorizationGateway
    val onboardingRepository: OnboardingRepository
    val authorizationNavigation: AuthorizationNavigation
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface AuthorizationDependenciesComponent : AuthorizationDependencies