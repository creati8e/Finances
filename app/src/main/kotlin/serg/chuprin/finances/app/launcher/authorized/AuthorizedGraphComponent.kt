package serg.chuprin.finances.app.launcher.authorized

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.main.dependencies.AuthorizedGraphLauncherDependencies
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 04.05.2020.
 */
@[ScreenScope Component(dependencies = [AuthorizedGraphLauncherDependencies::class])]
interface AuthorizedGraphComponent : ViewModelComponent<AuthorizationGraphLauncherViewModel> {

    companion object {
        fun get(): AuthorizedGraphComponent {
            val dependencies = Injector.getAuthorizedGraphLauncherDependencies()
            return DaggerAuthorizedGraphComponent
                .builder()
                .authorizedGraphLauncherDependencies(dependencies)
                .build()
        }
    }

}