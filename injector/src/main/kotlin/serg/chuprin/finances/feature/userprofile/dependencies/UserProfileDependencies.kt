package serg.chuprin.finances.feature.userprofile.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.api.domain.repository.DataRepository
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.navigation.UserProfileNavigation

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
interface UserProfileDependencies {

    val userRepository: UserRepository
    val dataRepository: DataRepository
    val onboardingRepository: OnboardingRepository

    val resourceManger: ResourceManger
    val dataPeriodFormatter: DataPeriodFormatter
    val authorizationGateway: AuthorizationGateway
    val userProfileNavigation: UserProfileNavigation
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface UserProfileDependenciesComponent : UserProfileDependencies