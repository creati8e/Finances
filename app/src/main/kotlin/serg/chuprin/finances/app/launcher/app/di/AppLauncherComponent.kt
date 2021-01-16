package serg.chuprin.finances.app.launcher.app.di

import dagger.Component
import serg.chuprin.finances.app.launcher.app.AppLauncherViewModel
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@[ScreenScope Component(dependencies = [AppLauncherDependencies::class])]
interface AppLauncherComponent : ViewModelComponent<AppLauncherViewModel> {

    companion object {

        fun get(): AppLauncherComponent {
            return DaggerAppLauncherComponent
                .builder()
                .appLauncherDependencies(
                    DaggerAppLauncherDependenciesComponent
                        .builder()
                        .coreDependenciesProvider(CoreDependenciesComponent.get())
                        .build()
                )
                .build()
        }

    }

}