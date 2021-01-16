package serg.chuprin.finances.app.launcher.app.di

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
interface AppLauncherDependencies {
    val authorizationGateway: AuthorizationGateway
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface AppLauncherDependenciesComponent : AppLauncherDependencies