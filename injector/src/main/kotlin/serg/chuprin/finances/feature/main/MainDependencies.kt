package serg.chuprin.finances.feature.main

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreGatewaysProvider
import serg.chuprin.finances.core.api.di.provider.CoreRepositoriesProvider
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
interface MainDependencies {
    val onboardingRepository: OnboardingRepository
    val authorizationGateway: AuthorizationGateway
}

@Component(
    dependencies = [
        CoreGatewaysProvider::class,
        CoreRepositoriesProvider::class
    ]
)
interface MainDependenciesComponent : MainDependencies