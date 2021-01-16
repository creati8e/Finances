package serg.chuprin.finances.app.launcher.authorized.di

import dagger.Component
import serg.chuprin.finances.app.launcher.authorized.AuthorizationGraphLauncherViewModel
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent

/**
 * Created by Sergey Chuprin on 04.05.2020.
 */
@[ScreenScope Component(dependencies = [AuthorizedGraphLauncherDependencies::class])]
interface AuthorizedGraphComponent : ViewModelComponent<AuthorizationGraphLauncherViewModel> {

    companion object {

        fun get(): AuthorizedGraphComponent {
            return DaggerAuthorizedGraphComponent.builder()
                .authorizedGraphLauncherDependencies(
                    DaggerAuthorizedGraphLauncherDependenciesComponent
                        .builder()
                        .coreDependenciesProvider(CoreDependenciesComponent.get())
                        .build()
                )
                .build()
        }

    }

}