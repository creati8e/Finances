package serg.chuprin.finances.feature.authorization.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreNavigationProvider
import serg.chuprin.finances.core.api.presentation.navigation.AuthorizationNavigation

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface AuthorizationDependencies {
    val authorizationNavigation: AuthorizationNavigation
}

@Component(dependencies = [CoreNavigationProvider::class])
interface AuthorizationDependenciesComponent : AuthorizationDependencies