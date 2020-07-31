package serg.chuprin.finances.feature.userprofile.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.UserRepository

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
interface UserProfileDependencies {
    val userRepository: UserRepository
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface UserProfileDependenciesComponent : UserProfileDependencies