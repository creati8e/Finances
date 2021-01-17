package serg.chuprin.finances.feature.userprofile.di

import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.api.domain.repository.DataRepository
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilder
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.userprofile.presentation.UserProfileNavigation

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
interface UserProfileDependencies : FeatureDependencies {

    val userRepository: UserRepository
    val dataRepository: DataRepository
    val onboardingRepository: OnboardingRepository

    val resourceManger: ResourceManger
    val dataPeriodFormatter: DataPeriodFormatter
    val authorizationGateway: AuthorizationGateway
    val userProfileNavigation: UserProfileNavigation
    val dataPeriodTypePopupMenuCellsBuilder: DataPeriodTypePopupMenuCellsBuilder
}