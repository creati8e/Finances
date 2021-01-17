package serg.chuprin.finances.feature.authorization.di

import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.feature.authorization.presentation.AuthorizationNavigation

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface AuthorizationDependencies : FeatureDependencies {
    val authorizationGateway: AuthorizationGateway
    val onboardingRepository: OnboardingRepository
    val authorizationNavigation: AuthorizationNavigation
}