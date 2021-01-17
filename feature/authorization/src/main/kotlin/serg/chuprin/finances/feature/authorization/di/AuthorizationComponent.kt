package serg.chuprin.finances.feature.authorization.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.authorization.presentation.model.viewmodel.AuthorizationViewModel
import serg.chuprin.finances.feature.authorization.presentation.view.AuthorizationFragment

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@[ScreenScope Component(dependencies = [AuthorizationDependencies::class])]
interface AuthorizationComponent :
    ViewModelComponent<AuthorizationViewModel>,
    InjectableComponent<AuthorizationFragment> {

    companion object {

        fun get(dependencies: AuthorizationDependencies): AuthorizationComponent {
            return DaggerAuthorizationComponent
                .builder()
                .authorizationDependencies(dependencies)
                .build()
        }

    }

}