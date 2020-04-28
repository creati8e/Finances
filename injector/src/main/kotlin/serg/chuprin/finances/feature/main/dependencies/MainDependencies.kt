package serg.chuprin.finances.feature.main.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
interface MainDependencies {
    val onboardingRepository: OnboardingRepository
    val authorizationGateway: AuthorizationGateway
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface MainDependenciesComponent : MainDependencies