package serg.chuprin.finances.app.di.feature.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.feature.authorization.presentation.di.AuthorizationDependencies

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
@Component(dependencies = [CoreDependenciesProvider::class])
internal interface AuthorizationDependenciesComponent : AuthorizationDependencies