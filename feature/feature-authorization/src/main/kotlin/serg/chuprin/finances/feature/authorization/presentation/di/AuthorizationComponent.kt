package serg.chuprin.finances.feature.authorization.presentation.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.authorization.dependencies.AuthorizationDependencies
import serg.chuprin.finances.feature.authorization.presentation.model.viewmodel.AuthorizationViewModel
import serg.chuprin.finances.feature.authorization.presentation.view.AuthorizationFragment
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@[ScreenScope Component(dependencies = [AuthorizationDependencies::class])]
interface AuthorizationComponent :
    ViewModelComponent<AuthorizationViewModel>,
    InjectableComponent<AuthorizationFragment> {

    companion object {

        fun get(): AuthorizationComponent {
            return DaggerAuthorizationComponent
                .builder()
                .authorizationDependencies(Injector.getAuthorizationDependencies())
                .build()
        }

    }

}