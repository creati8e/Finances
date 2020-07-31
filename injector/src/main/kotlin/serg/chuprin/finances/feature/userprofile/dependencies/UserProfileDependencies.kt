package serg.chuprin.finances.feature.userprofile.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
interface UserProfileDependencies {
    val userRepository: UserRepository
    val resourceManger: ResourceManger
    val dataPeriodFormatter: DataPeriodFormatter
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface UserProfileDependenciesComponent : UserProfileDependencies