package serg.chuprin.finances.feature.authorization.presentation.di

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

interface AuthorizationDependenciesProvider {
    val authorizationDependencies: AuthorizationDependencies
}