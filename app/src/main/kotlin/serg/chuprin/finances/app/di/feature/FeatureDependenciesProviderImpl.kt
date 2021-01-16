package serg.chuprin.finances.app.di.feature

import serg.chuprin.finances.app.di.feature.dependencies.DaggerAuthorizationDependenciesComponent
import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.feature.authorization.presentation.di.AuthorizationDependencies

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
class FeatureDependenciesProviderImpl : FeatureDependenciesProvider {

    override val authorizationDependencies: AuthorizationDependencies
        get() {
            return DaggerAuthorizationDependenciesComponent
                .builder()
                .coreDependenciesProvider(CoreDependenciesComponent.get())
                .build()
        }

}